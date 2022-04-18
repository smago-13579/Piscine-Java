package edu.school21.printer.app;

import edu.school21.printer.logic.Display;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

        checkParameters(args);

        try {
            char white = args[0].charAt(0);
            char black = args[1].charAt(0);
            Display display = new Display(white, black, "/resources/image.bmp");
            display.display();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkParameters(String[] args) {
        if (args.length != 2) {
            System.out.println("Specify 2 input parameters: characters for white and black pixels");
            System.exit(-1);
        }

        if (args[0].length() != 1 || args[1].length() != 1) {
            System.out.println("First and second parameters must be a single characters");
            System.exit(-1);
        }
    }
}