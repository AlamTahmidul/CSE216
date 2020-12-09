import javax.xml.transform.Result;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WordCounter {
    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get(".\\src\\Sample File Generator\\text_generator\\files"); // path to the folder where input
    // text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get(".\\src\\Output Files\\output.txt"); // path to the
    // output plain-text (.txt) file
    public static final int  NUMBER_OF_THREADS     = 5;                // max. number of threads to spawn

    public static void main(String... args) {
        // your implementation of how to run the WordCounter as a stand-alone multi-threaded program
        long time1 = System.currentTimeMillis();
        ArrayList<String> txtFiles = new ArrayList<>();
        // Create Tasks
        ArrayList<Callable<TreeMap<String, TreeMap<String, Integer>>>> tasks = new ArrayList<>();
        try (Stream<Path> paths = Files.list(FOLDER_OF_TEXT_FILES)) {
            paths.forEach(f -> {
                if (f.getFileName().toString().endsWith(".txt")) {
                    tasks.add(new FileReadTask(f));
                    txtFiles.add(f.getFileName().toString().substring(0, f.getFileName().toString().indexOf(".txt")));
                }
            });
        } catch (IOException e) {
            System.out.println("Invalid directory of input files. Directory does not exist.");
            System.out.println("Exiting...");
            return;
        }

        // Create threads and Start tasks
        int threads = NUMBER_OF_THREADS;
        if (threads <= 0)
            threads = 1;
        ExecutorService executors = Executors.newFixedThreadPool(threads);
        try {
            System.out.println("Reading Files...");
            List<Future<TreeMap<String, TreeMap<String, Integer>>>> results = executors.invokeAll(tasks);
            createFile(results.get(0), txtFiles);

            // Tasks finished
            System.out.println("Task done! Terminating threads...");
            executors.shutdown();

            try {
                if (!executors.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.out.println("Shutdown Unresponsive for 5 seconds. Forcing termination...");
                    executors.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                executors.shutdownNow();
            } finally {
                System.out.println("Successfully Terminated!");
                System.out.println("\nFinished in " + (System.currentTimeMillis() - time1) / 100.0 + " seconds.");
                System.out.println("With " + threads + " threads");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void createFile(Future<TreeMap<String, TreeMap<String, Integer>>> treeMapFuture,
                                   ArrayList<String> txtFiles) {
        TreeMap<String, TreeMap<String, Integer>> tmToOut = null;
        try {
            tmToOut = treeMapFuture.get();
            File outputFile = new File(String.valueOf(WORD_COUNT_TABLE_FILE.toAbsolutePath()));
            if (outputFile.createNewFile()) {
                System.out.println("File created! Modifying File...");
            } else {
                System.out.println("File exists! Modifying the file...");
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        } finally {
            if (tmToOut.size() == 1 && tmToOut.containsKey("")) {
                try {
                    FileWriter fileWriter = new FileWriter(String.valueOf(WORD_COUNT_TABLE_FILE.toAbsolutePath()));
                    fileWriter.write("");
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        assert tmToOut != null;
        if (tmToOut.size() == 1 && tmToOut.containsKey("")) return;
        build(tmToOut, txtFiles);

        // Add to File

        // Max Width of the columns (file names)
        int max_width = txtFiles.stream().max(Comparator.comparingInt(String::length)).map(String::length).orElse(0);
        String widthTemplate = "%-%is".replaceFirst("%-%i", "%-" + (max_width + 2));
        // Max Width of the first column
        int max_word_length =
                tmToOut.keySet().stream().max(Comparator.comparingInt(String::length)).map(String::length).orElse(0);
        String widthWordTemplate = "%-%is".replaceFirst("%-%i", "%-" + (max_word_length + 1));

        String header = IntStream.range(0, max_word_length + 1).mapToObj(i -> " ").collect(Collectors.joining(""));
        header += txtFiles.stream().map(s -> String.format(widthTemplate, s)).collect(Collectors.joining());
        header += String.format(widthTemplate, "Total") + "\n";
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(WORD_COUNT_TABLE_FILE.toAbsolutePath()));
            fileWriter.write(header);
            String toFile = "";
            for (Map.Entry<String, TreeMap<String, Integer>> entry : tmToOut.entrySet()) {
                toFile += String.format(widthWordTemplate, entry.getKey()); // Add Word ( entry.getKey() )

                // Add the counters
                String temp = "";
                Set<String> set = entry.getValue().keySet();
                temp += set.stream().map(s -> String.format(widthTemplate,
                        entry.getValue().get(s).toString())).collect(Collectors.joining());

                int total = entry.getValue().values().stream().reduce(0, Integer::sum);
                toFile += temp + total + "\n";
            }
            toFile += "------File Ends Here------";
            fileWriter.write(toFile);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Output File Location: " + WORD_COUNT_TABLE_FILE.toAbsolutePath());
    }

    /**
     * Adds filenames that are not already present with the corresponding word
     *
     * @param tmToOut
     * @param txtFiles
     * @return
     */
    private static TreeMap<String, TreeMap<String, Integer>> build(TreeMap<String, TreeMap<String, Integer>> tmToOut, ArrayList<String> txtFiles) {
        for (Map.Entry<String, TreeMap<String, Integer>> entry : tmToOut.entrySet()) {
            // Key: Word, Value: TreeMap<String, Integer> -> Filename, Counter
            for (String i : txtFiles) {
                if (!entry.getValue().containsKey(i)) {
//                    System.out.println(i);
                    entry.getValue().putIfAbsent(i, 0);
                }
            }
        }
        return tmToOut;
    }
}

class FileReadTask implements Callable<TreeMap<String, TreeMap<String, Integer>>> {
    Path pathToFile;
    private static TreeMap<String, TreeMap<String, Integer>> treeMap = new TreeMap<>();

    public FileReadTask(Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    /**
     * Reads from a file, saves the information in a TreeMap and returns the TreeMap
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public synchronized TreeMap<String, TreeMap<String, Integer>> call() throws Exception {
        synchronized (treeMap) {
            try {
                String regex = "[ .,:;!?'()_\"\r\n]+";
                String[] content = (new String(Files.readAllBytes(pathToFile))).split(regex);
                for (String i : content) {
                    String key = i.toLowerCase(Locale.ROOT);
                    TreeMap<String, Integer> newTm = new TreeMap<>();
                    String filename = pathToFile.getFileName().toString().substring(0,
                            pathToFile.getFileName().toString().indexOf(".txt"));

                    if (treeMap.containsKey(key)) {
                        // If the filename is already in the map with the given word then just update the counter
                        if (treeMap.get(key).containsKey(filename)) {
                            TreeMap<String, Integer> temp = treeMap.get(key);
                            temp.replace(filename, temp.get(filename) + 1);
                            treeMap.replace(key, temp);
                        } else {
                            // Otherwise, just add the new file and put counter to 1
                            treeMap.get(key).putIfAbsent(filename, 1);
                        }
                    } else {
                        newTm.put(filename, 1);
                        treeMap.putIfAbsent(key, newTm);
                    }
                }
//                System.out.println(Arrays.toString(content));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return treeMap;
        }
    }
}