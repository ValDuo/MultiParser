import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class BigCSVCutter extends CSVReader {

    public BigCSVCutter(String pathname) {
        super(pathname);
    }
    protected void extractInFolder(File folder){
        ArrayList<String> lines = readLikeCSV();
        int countLine = 0;
        int countFile = 0;
        Date date = new Date();
        String dateStr = String.format("%d-%d-%d", date.getDay(), date.getMonth(), date.getYear()-100);
        for(String line:lines){
            //TODO: make this shit fine
            //TODO: i'm so tired
            //TODO: maybe we will finish it TODAY

        }

    }
    protected void createFolder(String path) throws IOException{
        File folder = new File(path);
        boolean created = folder.mkdir();
        if (created){
            extractInFolder(folder);
        }
        else{
            throw new IOException("Не удалось создать папку");
        }
    }

}
