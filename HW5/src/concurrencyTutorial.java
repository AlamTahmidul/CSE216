public class concurrencyTutorial extends Thread {
    @Override
    public void run() {
        System.out.printf("Running thread %d.%n", Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        concurrencyTutorial ct = new concurrencyTutorial();

    }
}
