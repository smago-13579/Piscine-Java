import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int res = 0;
        int num;

        while (scan.hasNextInt()) {
            num = scan.nextInt();

            if (num != 42) {
                num = sumOfNum(num);
                res += primeNum(num);
            } else {
                System.out.print("Count of coffee - request - ");
                System.out.println(res);
                scan.close();
                System.exit(0);
            }
        }
        scan.close();
    }

    public static int primeNum(int num) {
        int i = 2;

        if (num < 2) {
            return 0;
        } else if (num == 2) {
            return 1;
        }

        while (i * i < num && num % i != 0) {
            i++;
        }

        if (num % i != 0) {
            return 1;
        }
        return 0;
    }

    public static int sumOfNum(int num) {
        int result = 0;

        while (num != 0) {
            result += num % 10;
            num /= 10;
        }
        return result;
    }
}