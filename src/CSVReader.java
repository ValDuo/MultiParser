import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class CSVReader{
    String path;
    File csvFile;
    CSVReader(String path) throws IOException{
        this.path = path;
        csvFile = new File(path);
        if (!csvFile.isFile())
            throw new IOException(String.format("не найден файл %s",path));
    }
    public ArrayList<ArrayList<String>> readLines(){
        Scanner sc;

        try {
            sc = new Scanner(csvFile);
        }
        catch (FileNotFoundException exception){
            return null;
        }
        ArrayList<ArrayList<String>> strings = new ArrayList<>();
        while(sc.hasNext()){
            String line = sc.nextLine();
            line = line.strip();
            String [] words = line.split(";");
            ArrayList<String> wordsList = new ArrayList<>(List.of(words));
            strings.add(wordsList);
        }
        return strings;
    }
    public void printLines(){
        ArrayList<ArrayList<String>> lines = readLines();
        for(ArrayList<String> line:lines){
            for(String word:line){
                System.out.print(word);
            }
            System.out.println();
        }
    }
}
