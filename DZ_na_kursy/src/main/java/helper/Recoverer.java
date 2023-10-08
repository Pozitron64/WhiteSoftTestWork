package helper;

import java.util.ArrayList;
import java.util.Map;

public class Recoverer {
    private ArrayList<String> arrayLineData;
    private Map<String, String> mapReplacement;

    public Recoverer() {
    }

    public void applyChanges(ArrayList<String> arrayLineData, Map<String, String> mapReplacement) {
        this.arrayLineData = arrayLineData;
        this.mapReplacement = mapReplacement;
        for (int index = 0; index < arrayLineData.size(); index++) {
            fixLine(index);
        }
        this.arrayLineData.removeIf(lineData -> lineData.equals(""));
    }

    public void fixLine(int index) {
        for (String replace : mapReplacement.keySet()) {
            if (arrayLineData.get(index).contains(replace)) {
                String source = mapReplacement.get(replace);
                if (source == null) {
                    String resLine = arrayLineData.get(index).replace(replace, "");
                    arrayLineData.set(index, resLine);
                    continue;
                }
                String resLine = arrayLineData.get(index).replace(replace, source);
                arrayLineData.set(index, resLine);
            }
        }
    }

    public ArrayList<String> getArrayLineData() {
        return arrayLineData;
    }

    public void setArrayLineData(ArrayList<String> arrayLineData) {
        this.arrayLineData = arrayLineData;
    }

    public Map<String, String> getMapReplacement() {
        return mapReplacement;
    }

    public void setMapReplacement(Map<String, String> mapReplacement) {
        this.mapReplacement = mapReplacement;
    }
}
