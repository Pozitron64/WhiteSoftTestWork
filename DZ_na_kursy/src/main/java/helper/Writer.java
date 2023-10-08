package helper;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Writer {
    private String resultFileName;
    private Gson gson = new Gson();

    public Writer(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public void writeIntoFile(ArrayList arrayLineData) {
        String array = gson.toJson(arrayLineData);
        try {
            Files.write(Paths.get(resultFileName), array.getBytes());
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл");
        }
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }
}
