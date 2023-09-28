import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Repairer {
    private ArrayList<String> arrayLineData;
    private Map<String, String> mapReplacement;
    private String urlData;
    private String replacementFileName;
    private String resultFileName;
    private static JSONParser parser = new JSONParser();

    public Repairer(String urlData, String replacementFileName, String resultFileName) {
        this.urlData = urlData;
        this.replacementFileName = replacementFileName;
        this.resultFileName = resultFileName;
        this.mapReplacement = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.compareTo(o2) == 0)
                    return 0;
                if (o1.length() > o2.length())
                    return -1;
                else
                    return 1;
            }
        });
        this.arrayLineData = new ArrayList<String>();
    }

    public void applyChanges() {
        for (int index = 0; index < arrayLineData.size(); index++) {
            fixLine(index);
        }
        clearEmptyLine();
    }

    public void fixLine(int index) {
        for (String replace : mapReplacement.keySet()) {
            if (arrayLineData.get(index).indexOf(replace) >= 0) {
                if (mapReplacement.get(replace) == null) {
                    arrayLineData.set(index, arrayLineData.get(index).replace(replace, ""));
                    continue;
                }
                arrayLineData.set(index, arrayLineData.get(index).replace(replace, mapReplacement.get(replace)));
            }
        }
    }

    public void clearEmptyLine() {
        for (int index = arrayLineData.size() - 1; index >= 0; index--) {
            if (arrayLineData.get(index) == ""){
                arrayLineData.remove(index);
            }
        }
    }

    public void writeResultIntoFile() {
        JSONArray outJsonArray = new JSONArray();
        for (String sdf : arrayLineData) {
            outJsonArray.add(sdf);
        }
        try {
            Files.write(Paths.get(resultFileName), outJsonArray.toJSONString().getBytes());
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл");
        }
    }

    public void initArrayLineData() {
        getJsonArrayByUrl(urlData).forEach(dataObject -> {
            arrayLineData.add((String) dataObject);
        });

    }

    public void initMapReplacement() {
        try {
            JSONArray replacement = (JSONArray) parser.parse(getJsonFile(replacementFileName));
            replacement.forEach(replacementObject -> {
                JSONObject replacementJsonObject = (JSONObject) replacementObject;
                mapReplacement.put((String) replacementJsonObject.get("replacement"),
                        (String) replacementJsonObject.get("source"));
            });
        } catch (ParseException e) {
            System.out.println("Не удалось пропарсить файл");
        }
    }

    private static String getJsonFile(String nameJsonFile) {
        StringBuilder builder = new StringBuilder();
        try {
            Files.readAllLines(Paths.get(nameJsonFile)).forEach(builder::append);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл");
        }
        return builder.toString();
    }

    private static JSONArray getJsonArrayByUrl(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return (JSONArray) parser.parse(response.body());
        } catch (ParseException e) {
            System.out.println("Не удалось пропарсить файл");
        } catch (URISyntaxException e) {
            System.out.println("Неправильный адрес url страницы");
        } catch (IOException e) {
            System.out.println("Не удалось прочитать url страницу");
        } catch (InterruptedException e) {
            System.out.println("Не удалось отправить url страницу");
        }
        return null;
    }

    public String getUrlData() {
        return urlData;
    }

    public void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    public String getReplacementFileName() {
        return replacementFileName;
    }

    public void setReplacementFileName(String replacementFileName) {
        this.replacementFileName = replacementFileName;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }
}
