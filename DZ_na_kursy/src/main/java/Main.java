import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {
    private static URL oracle;

    static {
        try {
            oracle = new URL("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private static final String DATA_FILE = "src/main/resources/data.json";
    private static final String REPLACEMENT_FILE = "src/main/resources/replacement.json";
    private static final String RESULT_FILE = "src/main/resources/result.json";
    private static ArrayList<String> arrayData = new ArrayList<String>();
    private static ArrayList<String> outData = new ArrayList<String>();
    private static Map<String, String> mapReplacement = new HashMap<>();
    private static Map<String, String> torReplacement = new HashMap<>();



    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray toData = (JSONArray) parser.parse(getJsonUrl(oracle));

            JSONArray replacement = (JSONArray) parser.parse(getJsonFile(REPLACEMENT_FILE));

            replacement.forEach(replacementObject -> {
                JSONObject replacementJsonObject = (JSONObject) replacementObject;
                mapReplacement.put((String) replacementJsonObject.get("replacement"),
                        (String) replacementJsonObject.get("source"));
            });
            toData.forEach(dataObject -> {
                arrayData.add((String) dataObject);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (String replacement : mapReplacement.keySet()) {
            torReplacement.put(replacement, mapReplacement.get(replacement));
        }
        for (String replacement1 : mapReplacement.keySet()) {
            for (String replacement2 : mapReplacement.keySet()) {
                if ((replacement2.indexOf(replacement1) == -1) || (replacement1 == replacement2)) {
                    continue;
                }
                torReplacement.put(replacement2.replace(replacement1, mapReplacement.get(replacement1)), mapReplacement.get(replacement2));
                torReplacement.remove(replacement2);
            }
        }


        for (String data : arrayData) {
            boolean der = true;
            for (String replacement : torReplacement.keySet()) {
                if (data.indexOf(replacement) == -1) {
                    continue;
                }
                if ((String) torReplacement.get(replacement) == null) {
                    data = data.replace(replacement, "");
                    if (data == "") {
                        der = false;
                    }
                    continue;
                }
                data = data.replace(replacement, (String) torReplacement.get(replacement));
            }
            if (der) {
                outData.add(data);
            }
        }

        JSONArray outJsonArray = new JSONArray();
        for (String sdf : outData) {
            outJsonArray.add(sdf);
        }

        try {
            Files.write(Paths.get(RESULT_FILE), outJsonArray.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String sdf : outData) {
            System.out.println(sdf);
        }
    }

    private static String getJsonFile(String nameJsonFile) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(nameJsonFile));
            lines.forEach(line -> builder.append(line));
        } catch (Exception ex) {
            ex.printStackTrace();
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
            e.printStackTrace();
        }
        return sun;
    }
}
//{
//    "replacement": "1",
//    "source": "l"
//  },