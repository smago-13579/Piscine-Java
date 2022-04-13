
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parse {
    private static final Map<String, String> formats = new HashMap<>();

    public static void parseSignatures(List<String> file) {
        for (String str : file) {
            if (!str.isEmpty()) {
                String[] data = str.split(",");
                formats.put(data[0].trim(), data[1].trim());
            }
        }
    }

    public static Map<String, String> getFormats() {
        return formats;
    }
}