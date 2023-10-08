import helper.Reader;
import helper.Recoverer;
import helper.Writer;

import java.util.ArrayList;
import java.util.Map;

public class Repairer {
    private Reader reader;
    private Recoverer recoverer;
    private Writer writer;
    private ArrayList<String> arrayLineData;
    private Map<String, String> mapReplacement;

    public Repairer(String urlData, String replacementFileName, String resultFileName) {
        reader = new Reader(urlData, replacementFileName);
        recoverer = new Recoverer();
        writer = new Writer(resultFileName);
    }

    public void initData() {
        arrayLineData = reader.getArrayLineData();
        mapReplacement = reader.getMapReplacement();
    }

    public void transformData() {
        recoverer.applyChanges(arrayLineData, mapReplacement);
        arrayLineData = recoverer.getArrayLineData();
        mapReplacement = recoverer.getMapReplacement();
    }

    public void writeFileData() {
        writer.writeIntoFile(arrayLineData);
    }

    private ArrayList<String> getArrayLineData() {
        return arrayLineData;
    }

    private void setArrayLineData(ArrayList<String> arrayLineData) {
        this.arrayLineData = arrayLineData;
    }

    private Map<String, String> getMapReplacement() {
        return mapReplacement;
    }

    private void setMapReplacement(Map<String, String> mapReplacement) {
        this.mapReplacement = mapReplacement;
    }

    public String getResultFileName() {
        return writer.getResultFileName();
    }

    public void setResultFileName(String resultFileName) {
        writer.setResultFileName(resultFileName);
    }

    public void setUrlData(String urlData) {
        reader.setUrlData(urlData);
    }

    public void setReplacementFileName(String replacementFileName) {
        reader.setReplacementFileName(replacementFileName);
    }

    public String getUrlData() {
        return reader.getUrlData();
    }

    public String getReplacementFileName() {
        return reader.getReplacementFileName();
    }
}
