
import java.util.Map;

public class Handle {
    private String buffer = "";

    public Handle() {}

    public void handling(Map<String, String> formats, String signature) {
        for (Map.Entry <String, String> item : formats.entrySet()) {
            if (signature.startsWith(item.getValue())) {
                this.buffer += item.getKey() + "\n";
                break;
            }
        }
    }

    public String getBuffer() {
        return buffer;
    }
}