

public class Program {

    public static void main(String[] args) {
        int num = 479598;
        int result = num % 10;

        num /= 10;
        result += num % 10;
        num /= 10;
        result += num % 10;
        num /= 10;
        result += num % 10;
        num /= 10;
        result += num % 10;
        num /= 10;
        result += num % 10;
        System.out.println(result);
    }
}
