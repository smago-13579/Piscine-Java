
import java.io.*;
import java.util.*;

public class Program {
    private static Scanner  scan = new Scanner(System.in);
    private static String   filepath;
    private static String   signature = "";

    public static void main(String[] args) {
        try (FileInputStream fin = new FileInputStream("signatures.txt")) {
            int i;

            while ((i = fin.read()) != -1) {
                signature += (char)i;
            }
            Parse parse = new Parse(signature);

            while (scan.hasNextLine()) {
                filepath = scan.nextLine();
                if (filepath.equals("42")) {
                    break;
                }

                System.out.println("PROCESSED");
                OpenFile File = new OpenFile(filepath);
                if (File.getBuffer().length() > 0) {
                    Handle handle = new Handle(parse.getFormats(), File.getBuffer());
                }

            }
        }
        catch(IOException ex) {
            scan.close();
            System.out.println(ex.getMessage());
        }
    }
}