package resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class BigCSVCutter extends CSV_IO {

    public BigCSVCutter(String pathname) {
        super(pathname);
    }
    protected void extractInFolder(File folder){
        ArrayList<String> lines = this.readLikeCSV();
        int countLine = 0;
        int countFile = 1;
        Date date = new Date();
        String dateStr = String.format("%d-%d-%d_", date.getDay(), date.getMonth(), date.getYear()-100);
        for(int i = 0; i < this.fileLen(); i+=50){
            CSV_IO curFile = new CSV_IO(dateStr+countFile);
            for(int j = i; j < i + 50; j++){
                if (j > lines.size())
                    return;
                curFile.write(lines.get(j));
            }
            countFile+=1;
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
