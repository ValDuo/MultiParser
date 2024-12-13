package resources;

import java.util.Date;

public class ParsingSrcDstFiles {
    CSV_IO source;
    CSV_IO distination;
    Date createdTime = new Date();
    ParsingSrcDstFiles(String srcPath){
        //this.createdTime = new Date();
        setSource(srcPath);
        setDistination();
    }
    ParsingSrcDstFiles(CSV_IO srcFile){
        setSource(srcFile);
        setDistination();
    }
    ParsingSrcDstFiles(String srcPath, String dstPath){
        //this.createdTime = new Date();
        setSource(srcPath);
        setDistination(dstPath);
    }
    private void setSource(String srcPath){
        this.source = new CSV_IO(srcPath);
    }
    private void setSource(CSV_IO srcFile){
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
        this.distination = new CSV_IO(path);
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
        CSV_IO newFile = new CSV_IO(path);
        if (newFile.isFile()){
            this.distination = newFile;
        }
        else{

        }

    }

    public CSV_IO getDistination() {
        return distination;
    }
    public CSV_IO getSource() {
        return source;
    }
}
