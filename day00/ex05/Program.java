import java.util.Scanner;


public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] names = new String[10];
        String[][] classes = new String[8][10];
        String[][][][] attendance = new String[10][31][10][1];
        String day;
        String time;
        String name = scanner.next();

        for (int i = 0; !name.equals("."); i++) {
            names[i] = name;
            name = scanner.next();
        }
        time = scanner.next();

        while (!time.equals(".")) {
            day = scanner.next();

            if (day.equals("MO")) {
                fillClasses(classes[1], time);
            } else if (day.equals("TU")) {
                fillClasses(classes[2], time);
            } else if (day.equals("WE")) {
                fillClasses(classes[3], time);
            } else if (day.equals("TH")) {
                fillClasses(classes[4], time);
            } else if (day.equals("FR")) {
                fillClasses(classes[5], time);
            } else if (day.equals("SA")) {
                fillClasses(classes[6], time);
            } else if (day.equals("SU")) {
                fillClasses(classes[7], time);
            }
            time = scanner.next();
        }
        name = scanner.next();

        while (!name.equals(".")) {
            int n, d, t;
            time = scanner.next();
            d = scanner.nextInt();
            String here = scanner.next();

            for (n = 0; n < names.length && !names[n].equals(name); n++);
            t = findDayAndClassOfWeek(d, classes, time);
            attendance[n][d][t][0] = here;
            name = scanner.next();
        }

        for (int i = 1; i < 31; i++) {
            findTimeOfClassAndPrint(i, classes);
        }

        for (int i = 0; i < names.length && names[i] != null; i++) {
            System.out.print(names[i]);
            findAttendanceAndPrint(attendance[i], classes, names[i].length());
        }
    }

    private static void findAttendanceAndPrint(String[][][] strings, String[][] classes, int len) {
        boolean isFirst = true;

        for (int i = 1; i < 31; i++) {
            for (int j = 0; j < 10 && classes[i % 7 + 1][j] != null; j++) {
                if (isFirst) {
                    isFirst = false;

                    for (int z = 0; z < 13 - len; z++) {
                        System.out.print(" ");
                    }
                }

                if (i > 9) {
                    System.out.print(" ");
                }

                if (strings[i][j][0] != null && strings[i][j][0].equals("HERE")) {
                    System.out.print(" 1|        ");
                } else if (strings[i][j][0] != null && strings[i][j][0].equals("NOT_HERE")) {
                    System.out.print("-1|        ");
                } else {
                    System.out.print("  |        ");
                }
            }
        }
        System.out.println();
    }

    public static int findDayAndClassOfWeek(int d, String[][] classes, String time) {
        int week = d % 7 + 1;
        int i = 0;

        for (; i < 10 && !classes[week][i].equals(time); i++);

        if (i < 10) {
            return i;
        }
        return 0;
    }

    public static void fillClasses(String[] classes, String time) {
        int i = 0;

        for (; i < classes.length && classes[i] != null; i++);

        if (i < 10) {
            classes[i] = time;
        }
    }

    private static void findTimeOfClassAndPrint(int i, String[][] classes) {
        if (i == 1) {
            System.out.print("     ");
        }
        int j = i % 7 + 1;

        for (int l = 0; l < 10 && classes[j][l] != null; l++) {
            System.out.print(classes[j][l] + ":00 ");

            if (j == 1) {
                System.out.print("MO  " + i + "|");
            } else if (j == 2) {
                System.out.print("TU  " + i + "|");
            } else if (j == 3) {
                System.out.print("WE  " + i + "|");
            } else if (j == 4) {
                System.out.print("TH  " + i + "|");
            } else if (j == 5) {
                System.out.print("FR  " + i + "|");
            } else if (j == 6) {
                System.out.print("SA  " + i + "|");
            } else if (j == 7) {
                System.out.print("SU  " + i + "|");
            }
        }

        if (i == 30) {
            System.out.println();
        }
    }
}