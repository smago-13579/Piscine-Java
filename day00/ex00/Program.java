

public class Program {
    public static void main(String[] args) {
        int num = 111111;
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
