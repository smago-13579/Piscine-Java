
public class Program {
    public static void main(String[] args) {
        int count;

        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("Specify a count argument using '--count='");
            System.exit(-1);
        }

        try {
            count = Integer.parseInt(args[0].substring("--count=".length()));

            if (count <= 0) {
                System.out.println("Incorrect count: " + count);
                System.exit(-1);
            }
            Print print = new Print();
            Thread egg = new Thread(new Egg(count, print));
            Thread hen = new Thread(new Hen(count, print));
            egg.start();
            hen.start();
        } catch (NumberFormatException e) {
            System.out.print("Illegal count argument: ");
            System.out.println(e.getMessage());
        }
    }
}
