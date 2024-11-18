import java.io.File;
import java.util.Date;

public class ParsingSrcDstFiles {
    CSVReader source;
    CSVReader distination;
    Date createdTime = new Date();
    ParsingSrcDstFiles(String srcPath){
        //this.createdTime = new Date();
        setSource(srcPath);
        setDistination();
    }
    ParsingSrcDstFiles(CSVReader srcFile){
        setSource(srcFile);
        setDistination();
    }
    ParsingSrcDstFiles(String srcPath, String dstPath){
        //this.createdTime = new Date();
        setSource(srcPath);
        setDistination(dstPath);
    }
    private void setSource(String srcPath){
        this.source = new CSVReader(srcPath);
    }
    private void setSource(CSVReader srcFile){
        this.source = srcFile;
    }
    private void setDistination() {
        int day = createdTime.getDay();
        int month = createdTime.getMonth();
        int year = createdTime.getYear();
        int hour = createdTime.getHours();
        int minute = createdTime.getMinutes();
        int second = createdTime.getSeconds();
        String path = "dstFiles/"+createdTime.toString().replace(":","_")+".csv";
        this.distination = new CSVReader(path);
        if (!this.distination.isFile() && !this.distination.isDirectory()){
            try{
                boolean created = this.distination.createNewFile();
                System.out.println(created);
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }
    private void setDistination(String path){
        CSVReader newFile = new CSVReader(path);
        if (newFile.isFile()){
            this.distination = newFile;
        }
        else{

        }

    }

    public CSVReader getDistination() {
        return distination;
    }
    public CSVReader getSource() {
        return source;
    }
}
