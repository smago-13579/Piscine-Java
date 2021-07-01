
import java.io.*;
import java.util.*;

public class Parse {
    private Map<String, String> formats = new HashMap<String, String>();

    public Parse(String str) {
        String key;
        String value;
        int begin = 0;
        int end;

        while ((end = str.indexOf(',', begin)) != -1) {
            key = str.substring(begin, end);
            begin = ++end;
            while (str.charAt(begin) == ' ' || str.charAt(begin) == ',') {
                begin++;
            }
            end = str.indexOf('\n', begin);
            value = str.substring(begin, end);
            begin = ++end;
            formats.put(key, value);
        }
    }

    public Map<String, String> getFormats() {
        return formats;
    }
}