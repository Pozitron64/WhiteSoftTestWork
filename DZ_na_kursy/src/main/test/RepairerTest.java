//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.TreeMap;
//
//public class RepairerTest {
//
//    @Test
//    void initMapReplacement(){
//        Repairer repairer = new Repairer("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json",
//                "src/main/testResources/replacement.json",
//                "src/main/testResources/result.json");
//        Map<String, String> expected = new TreeMap<>();
//        expected.put("Bla-bla-bla-blaaa, just some RANDOM tExT", null);
//        expected.put("parentheses - that is a smart word", "the better claim");
//        expected.put("sdshdjdskfm sfjsdif jfjfidjf", "Somewhere ages and ages hence:");
//        expected.put("d12324344rgg6f5g6gdf2ddjf", "wood");
//        expected.put("Ha-haaa, hacked you", "I doubted if I should ever come back");
//        expected.put("Random text, yeeep", "took");
//        expected.put("Skooby-dooby-doooo", "knowing how way");
//        expected.put("Emptry... or NOT!", "Had worn");
//        expected.put("An other text", null);
//        expected.put("1", "l");
//
//        repairer.initMapReplacement();
//
//        Assertions.assertEquals(expected, repairer.getMapReplacement());
//    }
//
//    @Test
//    void initArrayLineData(){
//        Repairer repairer = new Repairer("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json",
//                "src/main/testResources/replacement.json",
//                "src/main/testResources/result.json");
//        ArrayList<String> expected = new ArrayList<>();
//        expected.add("Two roads diverged in a yellow d12324344rgg6f5g6gdf2ddjf,");
//        expected.add("Robert Frost poetAnd sorry I cou1d not trave1 both");
//        expected.add("An other text");
//        expected.add("And be one trave1er, long I stood");
//        expected.add("And 1ooked down one as far as I cou1d");
//        expected.add("Bla-bla-bla-blaaa, just some RANDOM tExT");
//        expected.add("To where it bent in the undergrowth;");
//        expected.add("Then Random text, yeeep the other, as just as fair,");
//        expected.add("And having perhaps parentheses - that is a smart word,");
//        expected.add("Bla-bla-bla-blaaa, just some RANDOM tExT");
//        expected.add("Because it was grassy and wanted wear;");
//        expected.add("An other text");
//        expected.add("An other text");
//        expected.add("Though as for that the passing there");
//        expected.add("Emptry... or NOT! them rea11y about the same,");
//        expected.add("And both that morning equally lay");
//        expected.add("In 1eaves no step had trodden b1ack.");
//        expected.add("Oh, I kept the first for another day!");
//        expected.add("Yet Skooby-dooby-doooo 1eads on to way,");
//        expected.add("Ha-haaa, hacked you.");
//        expected.add("An other text");
//        expected.add("I shall be te11ing this with a sigh");
//        expected.add("sdshdjdskfm sfjsdif jfjfidjf");
//        expected.add("Two roads diverged in a d12324344rgg6f5g6gdf2ddjf, and I");
//        expected.add("I Random text, yeeep the one less traveled by,");
//        expected.add("And that has made a11 the difference.");
//        expected.add("Bla-bla-bla-blaaa, just some RANDOM tExT");
//
//        repairer.initArrayLineData();
//
//        Assertions.assertEquals(expected, repairer.getArrayLineData());
//    }
//
//    @Test
//    void fixLine(){
//        Repairer repairer = new Repairer("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json",
//                "src/main/testResources/replacement.json",
//                "src/main/testResources/result.json");
//        String expected = "Two roads diverged in a yellow wood,";
//
//        repairer.initArrayLineData();
//        repairer.initMapReplacement();
//        repairer.fixLine(0);
//
//        Assertions.assertEquals(expected, repairer.getArrayLineData().get(0));
//    }
//
//    @Test
//    void applyChanges(){
//        Repairer repairer = new Repairer("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json",
//                "src/main/testResources/replacement.json",
//                "src/main/testResources/result.json");
//        ArrayList<String> expected = new ArrayList<>();
//        expected.add("Two roads diverged in a yellow wood,");
//        expected.add("Robert Frost poetAnd sorry I could not travel both");
//        expected.add("And be one traveler, long I stood");
//        expected.add("And looked down one as far as I could");
//        expected.add("To where it bent in the undergrowth;");
//        expected.add("Then took the other, as just as fair,");
//        expected.add("And having perhaps the better claim,");
//        expected.add("Because it was grassy and wanted wear;");
//        expected.add("Though as for that the passing there");
//        expected.add("Had worn them really about the same,");
//        expected.add("And both that morning equally lay");
//        expected.add("In leaves no step had trodden black.");
//        expected.add("Oh, I kept the first for another day!");
//        expected.add("Yet knowing how way leads on to way,");
//        expected.add("I doubted if I should ever come back.");
//        expected.add("I shall be telling this with a sigh");
//        expected.add("Somewhere ages and ages hence:");
//        expected.add("Two roads diverged in a wood, and I");
//        expected.add("I took the one less traveled by,");
//        expected.add("And that has made all the difference.");
//
//        repairer.initArrayLineData();
//        repairer.initMapReplacement();
//        repairer.applyChanges();
//
//        Assertions.assertEquals(expected, repairer.getArrayLineData());
//    }
//
//    @Test
//    void writeResultIntoFile(){
//        Repairer repairer = new Repairer("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json",
//                "src/main/testResources/replacement.json",
//                "src/main/testResources/result.json");
//        String expected = "[\"Two roads diverged in a yellow d12324344rgg6f5g6gdf2ddjf,\",\"Robert Frost poetAnd sorry I cou1d not trave1 both\",\"An other text\",\"And be one trave1er, long I stood\",\"And 1ooked down one as far as I cou1d\",\"Bla-bla-bla-blaaa, just some RANDOM tExT\",\"To where it bent in the undergrowth;\",\"Then Random text, yeeep the other, as just as fair,\",\"And having perhaps parentheses - that is a smart word,\",\"Bla-bla-bla-blaaa, just some RANDOM tExT\",\"Because it was grassy and wanted wear;\",\"An other text\",\"An other text\",\"Though as for that the passing there\",\"Emptry... or NOT! them rea11y about the same,\",\"And both that morning equally lay\",\"In 1eaves no step had trodden b1ack.\",\"Oh, I kept the first for another day!\",\"Yet Skooby-dooby-doooo 1eads on to way,\",\"Ha-haaa, hacked you.\",\"An other text\",\"I shall be te11ing this with a sigh\",\"sdshdjdskfm sfjsdif jfjfidjf\",\"Two roads diverged in a d12324344rgg6f5g6gdf2ddjf, and I\",\"I Random text, yeeep the one less traveled by,\",\"And that has made a11 the difference.\",\"Bla-bla-bla-blaaa, just some RANDOM tExT\"]";
//
//        repairer.initArrayLineData();
//        repairer.writeResultIntoFile();
//        StringBuilder builder = new StringBuilder();
//        try {
//            Files.readAllLines(Paths.get("src/main/testResources/result.json")).forEach(builder::append);
//        } catch (IOException e) {
//            System.out.println("Не удалось прочитать файл");
//        }
//        System.out.println(builder);
//        Assertions.assertEquals(expected, builder.toString());
//    }
//}