public class Main {
    public static void main(String[] args) {
        Repairer repairer = new Repairer("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json",
                "DZ_na_kursy/src/main/testResources/replacement.json",
                "DZ_na_kursy/src/main/resources/result.json");
        repairer.initData();
        repairer.transformData();
        repairer.writeFileData();
    }
}