import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class WordCounter3 {
    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get(".\\src\\Text Files"); // path to the folder where input
    // text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get(".\\src\\Output Files\\output.txt"); // path to the
    // output plain-text (.txt) file
    public static final int  NUMBER_OF_THREADS     = 2;                // max. number of threads to spawn

    public static void main(String... args) {
        // your implementation of how to run the WordCounter as a stand-alone multi-threaded program
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
            System.out.println("Invalid directory of input files");
            e.printStackTrace();
        }

        // Create threads and Start tasks
        int threads = NUMBER_OF_THREADS;
        if (threads <= 0)
            threads = 1;
        ExecutorService executors = Executors.newFixedThreadPool(threads);
        try {
            System.out.println("Reading Files...");
            List<Future<TreeMap<String, TreeMap<String, Integer>>>> results = executors.invokeAll(tasks);
            System.out.println("Finished reading files! Creating Output File...");
//            for (Future f : results) {
//                TreeMap<String, TreeMap<String, Integer>> tmp = (TreeMap<String, TreeMap<String, Integer>>) f.get();
//                System.out.println(tmp.firstEntry());
//            }
            createFile(results.get(0), txtFiles);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        }
        // Add to File
        int max_width = txtFiles.stream().max(Comparator.comparingInt(String::length)).map(String::length).orElse(0);

//        System.out.println("Max Filename width: " + max_width);
        String filenames = ("%" + (max_width + 3) + "s").repeat(txtFiles.size());
//        tmToOut;
        System.out.println("Output File Location: " + WORD_COUNT_TABLE_FILE.toAbsolutePath());
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
    public TreeMap<String, TreeMap<String, Integer>> call() throws Exception {
        synchronized (treeMap) {
            try {
                String regex = "[ .,:;!?'\"\r\n]+";
                String[] content = (new String(Files.readAllBytes(pathToFile))).split(regex);
                for (String i : content) {
                    String key = i.toLowerCase(Locale.ROOT);
                    TreeMap<String, Integer> newTm = new TreeMap<>();
                    String filename = pathToFile.getFileName().toString();

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
                        newTm.put(pathToFile.getFileName().toString(), 1);
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