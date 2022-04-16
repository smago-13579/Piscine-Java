
public class MyThread extends Thread {
    private int num;
    private int firstEl;
    private int lastEl;
    private Print print;

    public MyThread(int num, int firstEl, int lastEl, Print print) {
        this.firstEl = firstEl;
        this.lastEl = lastEl;
        this.print = print;
        this.num = num;
    }

    @Override
    public void run() {
        print.print(firstEl, lastEl, num);
    }
}
