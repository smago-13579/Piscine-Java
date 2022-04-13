
public class Program {

    public static void main(String[] args) {
        boolean devMode = false;

        if (args.length > 0 && "--profile=dev".equals(args[0])) {
            devMode = true;
        }
        Menu menu = new Menu(devMode);

        menu.run();
    }
}