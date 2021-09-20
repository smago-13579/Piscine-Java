import java.util.Scanner;


public class Program {

    public static void printGrid(int i) {
        for (int j = 0; j <= i; j++) {
            System.out.print("#\t");
            if (j == i) {
                System.out.println();
            }
        }
    }

    public static void printEmptyLine(int[][] arr, int i, int index) {
        float part = (float)arr[0][1] / 10;
        float max_n = (float)arr[0][1];

        while ((float)arr[i][1] < max_n) {
            max_n -= part;
        }

        if (i + 1 == index) {
            while (max_n > 0) {
                printGrid(i);
                max_n -= part;
            }
        } else {
            max_n -= part;
            int num = arr[i + 1][1];
            while (num < max_n) {
                printGrid(i);
                max_n -= part;
            }
        }
    }

    public static int printNum(int[][] arr, int i, int index) {
        float max_n = (float)arr[0][1];
        float part = (float)arr[0][1] / 10;
        int coeff = 1;

        for ( ; (float)arr[i][1] < max_n - (part * (float)coeff); coeff++);

        for (int j = 0; j < i; j++) {
            System.out.print("#\t");
        }
        System.out.print(arr[i][1] + "\t");

        for (; i + 1 < index; i++) {
            if ((float)arr[i + 1][1] > max_n - (part * (float)coeff)) {
                System.out.print(arr[i + 1][1] + "\t");
            } else {
                break;
            }
        }
        System.out.println();
        printEmptyLine(arr, i, index);

        return i;
    }

    public static void printHistogram(int[][] arr, int index) {
        for (int i = 0; i < index; i++) {
            int len = i;
            int num = arr[i][1];

            if (i == 0) {
                while (i + 1 < index && arr[i + 1][1] == num) {
                    i++;
                }
                for (; len <= i; len++) {
                    System.out.print(num + "\t");
                }
                System.out.println();
                printEmptyLine(arr, i, index);
            } else {
                i = printNum(arr, i, index);
            }
        }
        for (int i = 0; i < index; i++) {
            System.out.print((char)arr[i][0] + "\t");
        }
        System.out.println();
    }

    public static int fillArray(int[][] arr, char c, int index) {
        for (int i = 0; i < index; i++) {
            if (arr[i][0] == (int)c) {
                arr[i][1] += 1;
                return 0;
            }
        }
        arr[index][0] = (int)c;
        arr[index][1] += 1;
        return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] arr = new int[65536][2];
        int index = 0;

        String str = scanner.nextLine();
        for (int i = 0; i < str.length(); i++) {
            index += fillArray(arr, str.charAt(i), index);
        }

        for (int left = 1; left < index; left++) {
            int[] tmp = arr[left];

            int i = left - 1;
            for (; i >= 0; i--) {
                if (tmp[1] > arr[i][1] || (tmp[1] == arr[i][1] && tmp[0] < arr[i][0])) {
                    arr[i + 1] = arr[i];
                } else {
                    break;
                }
            }
            arr[i + 1] = tmp;
        }
        if (index > 10) {
            index = 10;
        }
        printHistogram(arr, index);
    }
}