import java.io.File;
import java.util.Date;

public class ParsingSrcDstFiles {
    File source;
    File distination;
    Date createdTime;
    ParsingSrcDstFiles(String srcPath){
        this.createdTime = new Date();
        setSource(srcPath);
        setDistination();
    }
    ParsingSrcDstFiles(String srcPath, String dstPath){
        this.createdTime = new Date();
        setSource(srcPath);
        setDistination(dstPath);
    }
    private void setSource(String srcPath){
        this.source = new File(srcPath);
    }
    private void setDistination() {
        int day = createdTime.getDay();
        int month = createdTime.getMonth();
        int year = createdTime.getYear();
        int hour = createdTime.getHours();
        int minute = createdTime.getMinutes();
        int second = createdTime.getSeconds();
        this.distination = new File(String.format("%d-%d-%d %d:%d:%d_parsed.csv", year,month,day,hour,minute,second));
        if (!this.distination.isFile() && !this.distination.isDirectory()){
            try{
                this.distination.createNewFile();
            }
            catch (Exception e){

            }
        }
    }
    private void setDistination(String path){
        File newFile = new File(path);
        if (newFile.isFile()){
            this.distination = newFile;
        }
        else{

        }

    }

    public File getDistination() {
        return distination;
    }
    public File getSource() {
        return source;
    }
}
