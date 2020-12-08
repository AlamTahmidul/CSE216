import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class WordCounter2 {

    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get(".\\src\\Text Files"); // path to the folder where input
    // text
    // files
    // are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get(".\\Output Files\\output.txt"); // path to the output
    // plain-text (
    // .txt) file
    public static final int  NUMBER_OF_THREADS     = 1;                // max. number of threads to spawn

    TreeMap<String, Integer> tp = new TreeMap<>();
    public static void main(String... args) {
        // your implementation of how to run the WordCounter as a stand-alone multi-threaded program
        long time1 = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        try (Stream<Path> paths = Files.list(FOLDER_OF_TEXT_FILES)) {
            paths.forEach(f -> {
                Runnable runnableTask = () -> {
                    fileRead(f);
                };
                System.out.println("Running task on: " + f.getFileName().toString().substring(0,
                        f.getFileName().toString().indexOf(".txt")) +
                        "...");
                executor.submit(runnableTask);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Task done! Terminating threads...");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Shutdown Unresponsive for 5 seconds. Forcing termination...");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        } finally {
            System.out.println("Successfully Terminated!");
            System.out.println("Process took: " + (System.currentTimeMillis() - time1) + " ms");
        }
    }

    synchronized static void fileRead(Path path) {
        try {
            String regex = "[ [.,:;!?'\"][\r\n|\n|\r]]+";
            String[] content = (new String(Files.readAllBytes(path))).split(regex);
            System.out.println(Arrays.toString(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}