import java.util.Scanner;

public class Program {
    public static void IllegalArgument() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }

    public static int ScanInt(Scanner scan) {
        if (scan.hasNextInt() == false) {
            scan.close();
            IllegalArgument();
        }
        int num = scan.nextInt();

        return num;
    }

    public static void PrintTests(long tests) {
        long len = 1;
        long i = 0;
        for (; tests / len > 10; len *= 10);
        for (int week = 1; tests != 0; week++, len /= 10) {
            i = tests / len;
            System.out.print("Week " + week + " ");
            if (i == 1) {
                System.out.println("=>");
            }
            else if (i == 2) {
                System.out.println("==>");
            }
            else if (i == 3) {
                System.out.println("===>");
            }
            else if (i == 4) {
                System.out.println("====>");
            }
            else if (i == 5) {
                System.out.println("=====>");
            }
            else if (i == 6) {
                System.out.println("======>");
            }
            else if (i == 7) {
                System.out.println("=======>");
            }
            else if (i == 8) {
                System.out.println("========>");
            }
            else if (i == 9) {
                System.out.println("=========>");
            }
            tests = tests - len * i;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long tests = 0;
        String str;

        for (int week = 1; week < 19; week++) {
            long last_eval = 10;
            long new_eval = 0;
            str = scan.next();

            if (str.equals("42")) {
                PrintTests(tests);
                break;
            }
            else if (str.equals("Week") == false) {
                scan.close();
                IllegalArgument();
            }
            if (week != ScanInt(scan)) {
                scan.close();
                IllegalArgument();
            }
            for (int i = 0; i < 5; i++) {
                if ((new_eval = ScanInt(scan)) < 1 || new_eval > 9) {
                    scan.close();
                    IllegalArgument();
                }
                if (last_eval > new_eval) {
                    last_eval = new_eval;
                }
            }
            tests = tests * 10 + last_eval;
            if (week == 18) {
                PrintTests(tests);
                break;
            }
        }
        scan.close();
    }
}