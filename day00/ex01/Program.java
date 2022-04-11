import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int iter = 1;
        int i = 2;
        int num;

        if (scan.hasNextInt() == false) {
            System.err.println("Illegal Argument");
            scan.close();
            System.exit(-1);
        }
        num = scan.nextInt();

        if (num < 2) {
            System.err.println("Illegal Argument");
            scan.close();
            System.exit(-1);
        } else if (num == 2) {
            System.out.println("true 1");
        } else {
            while (i * i < num && num % i != 0) {
                i++;
                iter++;
            }

            if (num % i != 0) {
                System.out.print("true ");
            } else {
                System.out.print("false ");
            }
            System.out.println(iter);
        }
        scan.close();
    }
}
