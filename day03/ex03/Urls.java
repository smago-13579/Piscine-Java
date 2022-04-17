
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Urls {
    private boolean isAllDownloaded = false;
    private int key = 1;
    private Map<Integer, String> urlsList = new TreeMap<>();

    public Urls() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("files_urls.txt"));
        list = list.stream().map(s -> s = s.trim()).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        if (list.size() == 0) {
            throw new RuntimeException("There are no URLs in the file 'files_urls.txt'");
        }

        for (String str : list) {
            String[] keyValue = str.split("\\s+");
            urlsList.put(Integer.parseInt(keyValue[0]), keyValue[1]);
        }
    }

    public synchronized boolean isAllDownloaded() {
        return isAllDownloaded;
    }

    public int getUrlsListIndex(String str) {
        Set<Map.Entry<Integer, String>> set = urlsList.entrySet();

        for (Map.Entry entry : set) {
            if (entry.getValue().equals(str)) {
                return (int) entry.getKey();
            }
        }
        return -1;
    }

    public synchronized String getUrl() {
        if (!urlsList.containsKey(key)) {
            return null;
        }
        String str = urlsList.get(key++);

        if (!urlsList.containsKey(key)) {
            isAllDownloaded = true;
        }
        return str;
    }
}
