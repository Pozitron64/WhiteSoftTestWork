import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Repairer {
    private ArrayList<String> arrayLineData;
    private Map<String, String> mapReplacement;
    private ArrayList<String> outArrayLineData = new ArrayList<String>();
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
        for (Object line : arrayLineData) {
            boolean der = true;
            String result = line.toString();
            for (String replace : mapReplacement.keySet()) {
                if (!result.contains(replace)) {
                    continue;
                }
                if ((String) mapReplacement.get(replace) == null) {
                    result = result.replace(replace, "");
                    if (result == "") {
                        der = false;
                    }
                    continue;
                }
                result = result.replace(replace, (String) mapReplacement.get(replace));
            }
            if (der) {
                outArrayLineData.add(result);
            }
        }
    }

    public void writeResultIntoFile() {
        JSONArray outJsonArray = new JSONArray();
        for (String sdf : outArrayLineData) {
            outJsonArray.add(sdf);
        }

        try {
            Files.write(Paths.get(resultFileName), outJsonArray.toJSONString().getBytes());
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл");
        }
    }

    public void initArrayLineData() {
        try {
            JSONArray toData = (JSONArray) parser.parse(getJsonUrl(new URL(urlData)));
            toData.forEach(dataObject -> {
                arrayLineData.add((String) dataObject);
            });
        } catch (MalformedURLException e) {
            System.out.println("Нет доступа к указанному URL");
        } catch (ParseException e) {
            System.out.println("Не удалось пропарсить файл");
        }
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
            List<String> lines = Files.readAllLines(Paths.get(nameJsonFile));
            lines.forEach(line -> builder.append(line));
        } catch (IOException e){
            System.out.println("Не удалось прочитать файл");
        }
        return builder.toString();
    }

    private static String getJsonUrl(URL oracle) {
        String sun = "";
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                sun += inputLine;
            in.close();
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл");
        }
        return sun;
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
