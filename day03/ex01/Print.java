
public class Print {
    int count = 0;

    public synchronized void printEgg() throws InterruptedException {
        while (count != 0) {
            wait();
        }
        System.out.println("Egg");
        count = 1;
        notify();
    }

    public synchronized void printHen() throws InterruptedException {
        while (count != 1) {
            wait();
        }
        System.out.println("Hen");
        count = 0;
        notify();
    }
}
