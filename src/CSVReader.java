import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class CSVReader extends File{
    String pathname;
    File csvFile;
    ArrayList<ArrayList<String>> lines = null;
    public CSVReader(String pathname) {
        super(pathname);
    }

    public ArrayList<ArrayList<String>> readLines(){
        if(this.lines != null)
            return this.lines;
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
        this.lines = strings;
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
    public int fileLen(){
        return this.lines.size();
    }
}
