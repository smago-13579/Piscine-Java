
import java.util.Arrays;
import java.util.Random;

public class Program {
    private static int arraySize, threadsCount;
    private static int[] array;
    private static MyThread[] threads;
    private static Print print;

    public static void main(String[] args) {
        try {
            checkArguments(args);
            createAll();
            runThreads();
            System.out.println("Sum by threads: " + print.getSumFromMyThreads());
        } catch (NumberFormatException e) {
            System.out.print("Illegal argument: ");
            System.out.println(e.getMessage());
        }
    }

    private static void checkArguments(String[] args) {
        if (args.length != 2 || !args[0].startsWith("--arraySize=")
                || !args[1].startsWith("--threadsCount=")) {
            System.out.println("Specify arguments using '--arraySize=' && '--threadsCount='");
            System.exit(-1);
        }
        arraySize = Integer.parseInt(args[0].substring("--arraySize=".length()));
        threadsCount = Integer.parseInt(args[1].substring("--threadsCount=".length()));

        if (arraySize > 2_000_000 || threadsCount < 1 || threadsCount > arraySize) {
            System.out.println("Illegal argument for arraySize or threadsCount");
            System.exit(-1);
        }
    }

    private static void createAll() {
        int section, firstEl, lastEl = 0;
        int n = 0;
        array = new int[arraySize];
        threads = new MyThread[threadsCount];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1001);
        }
        System.out.println("Sum: " + Arrays.stream(array).sum());
        print = new Print(array);
        section = arraySize / threadsCount;

        for (int i = 0; i < (threads.length - 1); i++, n++) {
            firstEl = section * n;
            lastEl = firstEl + section - 1;
            threads[i] = new MyThread((i + 1), firstEl, lastEl, print);
        }

        if (arraySize % threadsCount != 0) {
            section = arraySize - (section * (threadsCount - 1));
        }

        if (threadsCount > 1) {
            threads[threads.length - 1] = new MyThread(threads.length,
                    lastEl + 1, lastEl + section, print);
        } else {
            threads[0] = new MyThread(1, 0, arraySize - 1, print);
        }
    }

    private static void runThreads() {
        try {
            for (int i = 0; i < threads.length; i++) {
                threads[i].start();
            }

            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
