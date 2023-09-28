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