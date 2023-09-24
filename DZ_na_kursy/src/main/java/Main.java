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
    public static void main(String[] args) {
        Repairer repairer = new Repairer("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json",
                "src/main/resources/replacement.json",
                "src/main/resources/result.json");
        repairer.initArrayLineData();
        repairer.initMapReplacement();
        repairer.applyChanges();
        repairer.writeResultIntoFile();
    }
}