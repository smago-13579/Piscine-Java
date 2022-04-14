import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    private static Scanner scanner = new Scanner(System.in);
    private static List<String> list;
    private static Path path, nPath;

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.out.println("An absolute path wasn't specified as a program argument");
            System.out.println("Specify an absolute path using '--current-folder='");
            System.exit(-1);
        }
        String absolutePath = args[0].substring("--current-folder=".length());
        path = Paths.get(absolutePath);
        ProgramUtils.checkPath(path);
        System.out.println(path);

        while (true) {
            exeCommand();
        }
    }

    private static void exeCommand() {
        String str = scanner.nextLine();
        list = Arrays.stream(str.trim().split("\\s+")).collect(Collectors.toList());

        if (list.size() == 1 && "exit".equals(list.get(0))) {
            System.exit(0);
        } else if (list.size() == 1 && "ls".equals(list.get(0))) {
            commandLS();
        } else if (list.size() == 2 && "cd".equals(list.get(0))) {
            commandCD();
        } else if (list.size() == 3 && "mv".equals(list.get(0))) {
            commandMV();
        } else {
            System.out.println("UNKNOWN COMMAND");
        }
    }

    private static void commandMV() {
        Path source = null;

        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path tmp : files) {
                if (tmp.getFileName().toString().equals(list.get(1))
                        && Files.isRegularFile(tmp)) {
                    source = tmp;
                    break;
                }
            }

            if (source == null) {
                System.out.println("mv: no such file: " + list.get(1));
                return;
            }

            if (isDirectory(list.get(2))) {
                Files.move(source, nPath.resolve(source.getFileName()),
                        StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(source, source.resolveSibling(Paths.get(list.get(2))));
            }
        } catch(IOException e) {
            System.out.println("Exception in method: commandMV");
            System.out.println(e.getMessage());
        }

    }

    private static void commandLS() {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path tmp : files) {
                long size;

                if (Files.isDirectory(tmp)) {
                    size = ProgramUtils.directorySize(tmp);
                } else {
                    size = Files.size(tmp);
                }
                System.out.println(tmp.getFileName() + " " + ProgramUtils.getSize(size));
            }
        } catch(IOException e) {
            System.out.println("Exception in method: commandLS");
            System.out.println(e.getMessage());
        }
    }

    private static void commandCD() {
        if (isDirectory(list.get(1))) {
            path = nPath;
        } else {
            System.out.println("cd: no such directory: " + list.get(1));
        }
        System.out.println(path);
    }

    private static boolean isDirectory(String strPath) {
        nPath = Paths.get(strPath);
        nPath = path.resolve(nPath).normalize();

        if (Files.isDirectory(nPath)) {
            return true;
        }
        return false;
    }
}
