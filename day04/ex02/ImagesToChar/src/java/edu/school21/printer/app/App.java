package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import edu.school21.printer.logic.Args;
import edu.school21.printer.logic.Display;

public class App {

    public static void main(String[] args) {
        try {
            Args jArgs = new Args();
            JCommander jCommander = new JCommander(jArgs);
            jCommander.parse(args);
            Display display = new Display(jArgs, "/resources/image.bmp");
            display.display();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}