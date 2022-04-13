
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {
    private static final Handle handle = new Handle();

    public static void main(String[] args) {
        try (FileOutputStream output = new FileOutputStream("result.txt");
             Scanner scan = new Scanner(System.in)) {
            Parse.parseSignatures(Files.readAllLines(Paths.get(("signatures.txt"))));

            while (true) {
                String filepath = scan.nextLine();

                if (filepath.equals("42")) {
                    output.write(handle.getBuffer().getBytes(), 0, handle.getBuffer().length());
                    break;
                }
                System.out.println("PROCESSED");
                NewFile file = new NewFile(filepath);

                if (file.getSignature().length() > 0) {
                    handle.handling(Parse.getFormats(), file.getSignature());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

