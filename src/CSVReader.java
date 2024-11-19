import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class CSVReader extends File{
    protected String pathname;
    protected File csvFile;
    protected ArrayList<ArrayList<String>> words = null;
    protected ArrayList<String> lines;
    public CSVReader(String pathname) {
        super(pathname);
        this.pathname = pathname;
        csvFile = new File(pathname);
    }
    public CSVReader(File file){
        this(file.getName());
    }

    public ArrayList<String> readLines(){
        if(this.lines != null)
            return this.lines;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.csvFile), "cp1251"));
        }
        catch (Exception exception){
            System.out.println(exception);
        }
        //ArrayList<ArrayList<String>> strings =
//        while(reader.){
//            String line = reader.nextLine();
//            line = line.strip();
//            String [] words = line.split(";");
//            ArrayList<String> wordsList = new ArrayList<>(List.of(words));
//            strings.add(wordsList);
//        }
        //this.lines = strings;
        //reader.close();
        ArrayList<String> lines = new ArrayList<>();
        if (reader != null) {
            reader.lines().forEach(lines::add);
        }
        this.lines = lines;
        return this.lines;
    }
    public void printLines(){

        ArrayList<String> lines= readLines();
        for(String line:lines){
            System.out.println(line);
        }
    }
    public ArrayList<String> readLikeCSV(){
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader;
        try {
           reader =  new BufferedReader(new FileReader(pathname));
        }

        catch (IOException exception){
            return null;
        }
        reader.lines().forEach(lines::add);

//        while (reader.hasNextLine())
//            lines.add(reader.nextLine());
        return lines;
    }
    public int fileLen(){
        if (this.lines != null)
            return this.lines.size();
        return 0;
    }
}
