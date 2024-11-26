import java.io.*;
import java.util.ArrayList;

public class CSV_IO extends File {
    protected String pathname;
    protected File csvFile;
    protected ArrayList<ArrayList<String>> words = null;
    protected ArrayList<String> lines;
    protected String mode = "r";

    public CSV_IO(String pathname, String mode) {
        super(pathname);
        this.pathname = pathname;
        this.csvFile = new File(pathname);
        this.mode = mode;
    }

    public CSV_IO(File file, String mode) {
        this(file.getName(), mode);
    }

    public CSV_IO(String pathname) {
        super(pathname);
        this.pathname = pathname;
        this.csvFile = new File(pathname);
    }

    public CSV_IO(File file) {
        this(file.getName());
    }


    public ArrayList<String> readLines() {
        if (this.lines != null)
            return this.lines;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.csvFile), "cp1251"));
        } catch (Exception exception) {
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

    public void printLines() {

        ArrayList<String> lines = readLines();
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public ArrayList<String> readLikeCSV() {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(pathname));
        } catch (IOException exception) {
            return null;
        }
        reader.lines().forEach(lines::add);
        return lines;
    }

    public int fileLen() {
        if (this.lines != null)
            return this.lines.size();
        return 0;
    }

    public boolean write(String string) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.csvFile, true), "cp1251"));
            writer.write(string);
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean writeCSVWord(String string) {
        return write(string + ";");
    }

    public boolean writeCSVLine(ArrayList<String> words) {
        boolean result;
        for (String word : words) {
            result = write(word + ";");
            if (!result) {
                return result;
            }

        }
        result = write("\n");
        return result;
    }
    public boolean writeCSVLine(String line){
        writeCSVWord(line);
        write("\n");
        return true;
    }
}

