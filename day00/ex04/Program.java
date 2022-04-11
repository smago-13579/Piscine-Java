import java.util.Scanner;


public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int[][] arr = new int[65536][2];
        int index = 0;

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

    public static void printGrid(int i) {
        for (int j = 0; j <= i; j++) {
            System.out.print("#\t");
        }
        System.out.println();
    }

    public static void printEmptyLine(int[][] arr, int i, int index) {
        float part = (float)arr[0][1] / 10;
        float maxN = (float)arr[0][1];
        int num;

        while ((float)arr[i][1] < maxN) {
            maxN -= part;
        }

        if (i + 1 == index) {
            while (maxN > 0) {
                printGrid(i);
                maxN -= part;
            }
        } else {
            maxN -= part;
            num = arr[i + 1][1];

            while (num < maxN) {
                printGrid(i);
                maxN -= part;
            }
        }
    }

    public static int printNum(int[][] arr, int i, int index) {
        float maxN = (float)arr[0][1];
        float part = (float)arr[0][1] / 10;
        int coeff = 1;

        for ( ; (float)arr[i][1] < maxN - (part * (float)coeff); coeff++);

        for (int j = 0; j < i; j++) {
            System.out.print("#\t");
        }
        System.out.print(arr[i][1] + "\t");

        for (; i + 1 < index; i++) {
            if ((float)arr[i + 1][1] > maxN - (part * (float)coeff)) {
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
}