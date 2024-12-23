import resources.BigCSVCutter;
import resources.CSV_IO;

import java.io.File;
public class CutterTest {
    public static void main(String[] args){
        CSV_IO BigCsvTest = new CSV_IO("/Users/dmitrijkostukov/IdeaProjects/parcing-of-kadastrs/src/Tests/TestFiles/bigcsv.csv");
        for(int i = 0; i < 1000; i++){
            BigCsvTest.writeCSVLine("test");
        }
        BigCSVCutter cutter = new BigCSVCutter(BigCsvTest.getAbsolutePath());
        System.out.println(cutter.cut());
    }
}
