import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long tests = 0;
        String str;

        for (int week = 1; week < 19; week++) {
            long lastEval = 10;
            long newEval;
            str = scan.next();

            if (str.equals("42")) {
                printTests(tests);
                break;
            } else if (!"Week".equals(str)) {
                scan.close();
                illegalArgument();
            }

            if (week != scanInt(scan)) {
                scan.close();
                illegalArgument();
            }

            for (int i = 0; i < 5; i++) {
                newEval = scanInt(scan);

                if (newEval < 1 || newEval > 9) {
                    scan.close();
                    illegalArgument();
                }

                if (lastEval > newEval) {
                    lastEval = newEval;
                }
            }
            tests = tests * 10 + lastEval;

            if (week == 18) {
                printTests(tests);
                break;
            }
        }
        scan.close();
    }

    public static void illegalArgument() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }

    public static int scanInt(Scanner scan) {
        if (!scan.hasNextInt()) {
            scan.close();
            illegalArgument();
        }
        int num = scan.nextInt();

        return num;
    }

    public static void printTests(long tests) {
        long len = 1;
        long i;

        for (; tests / len > 10; len *= 10);

        for (int week = 1; tests != 0; week++, len /= 10) {
            i = tests / len;
            System.out.print("Week " + week + " ");

            if (i == 1) {
                System.out.println("=>");
            } else if (i == 2) {
                System.out.println("==>");
            } else if (i == 3) {
                System.out.println("===>");
            } else if (i == 4) {
                System.out.println("====>");
            } else if (i == 5) {
                System.out.println("=====>");
            } else if (i == 6) {
                System.out.println("======>");
            } else if (i == 7) {
                System.out.println("=======>");
            } else if (i == 8) {
                System.out.println("========>");
            } else if (i == 9) {
                System.out.println("=========>");
            }
            tests = tests - len * i;
        }
    }
}