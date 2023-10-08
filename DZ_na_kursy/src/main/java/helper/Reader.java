package helper;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Reader {
    private String urlData;
    private String replacementFileName;
    private Gson gson = new Gson();

    public Reader(String urlData, String replacementFileName) {
        this.urlData = urlData;
        this.replacementFileName = replacementFileName;
    }

    public ArrayList getArrayLineData() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.urlData))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), ArrayList.class);
        } catch (URISyntaxException e) {
            System.out.println("Неправильный адрес url страницы");
        } catch (IOException e) {
            System.out.println("Не удалось прочитать url страницу");
        } catch (InterruptedException e) {
            System.out.println("Не удалось отправить url страницу");
        }
        return null;
    }

    public Map getMapReplacement() {
        StringBuilder builder = new StringBuilder();
        try {
            Files.readAllLines(Paths.get(this.replacementFileName)).forEach(builder::append);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл");
        }
        Type collectionType = new TypeToken<Collection<Line>>() {
        }.getType();
        return parseMap(gson.fromJson(builder.toString(), collectionType));
    }

    private Map parseMap(ArrayList<Line> arrayList) {
        Map<String, String> map = new TreeMap<>(Comparator.reverseOrder());
        for (Line line : arrayList) {
            map.put(line.getReplacement(), line.getSource());
        }
        return map;
    }

    public void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    public void setReplacementFileName(String replacementFileName) {
        this.replacementFileName = replacementFileName;
    }

    public String getUrlData() {
        return urlData;
    }

    public String getReplacementFileName() {
        return replacementFileName;
    }
}

class Line {
    @SerializedName("replacement")
    private String replacement;
    @SerializedName("source")
    private String source;

    public Line() {
    }

    public Line(String replacement, String source) {
        this.replacement = replacement;
        this.source = source;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
