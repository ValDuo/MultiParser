package resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class BigCSVCutter extends CSV_IO {

    public BigCSVCutter(String pathname) {
        super(pathname);
    }
    protected boolean extractInFolder(File folder){
        ArrayList<String> lines = this.readLikeCSV();
        int countLine = 0;
        int countFile = 1;
        stop:
        for(int i = 0; i < lines.size(); i+=50){
            CSV_IO curFile = new CSV_IO(folder.getAbsolutePath()+"/"+countFile+".csv");
            try {
                curFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
                return false;
            }
            for(int j = i; j < i + 50; j++){
                if (j > lines.size()) break stop;
                curFile.write(lines.get(j)+"\n");
            }
            countFile+=1;
        }
        return true;
    }

    protected File createFolder() throws IOException{
        String path = this.csvFile.getAbsolutePath();
        path = path.replaceAll("/.csv", "");
        path+="_cut";
        File folder = new File(path);
        boolean created = folder.mkdir();
        if (created){
            return folder;
        }
        else{
            throw new IOException("Не удалось создать папку");
        }
    }
    public boolean cut(){
        File folder;
        try {

            folder = createFolder();
        }
        catch (IOException e){
            System.out.println(e);
            return false;
        }
        boolean result = extractInFolder(folder);
        return result;

    }

}
