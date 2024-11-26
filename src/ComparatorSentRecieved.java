import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.zip.*;
import java.util.ArrayList;
public class ComparatorSentRecieved {
    private HashSet requestSet;
    private HashSet responseSet;
    private File requestFile;
    private File responseDirectory;
    ComparatorSentRecieved(String requestFilePath, String responseDirectoryPath) throws IOException{
        File request = new File(requestFilePath);
        File response = new File(responseDirectoryPath);
        checkFiles(request, response);
        this.requestFile = request;
        this.responseDirectory = response;
    }
    ComparatorSentRecieved(File requestFile, File responseDirectory)throws IOException{
        checkFiles(requestFile, responseDirectory);
        this.requestFile = requestFile;
        this.responseDirectory = responseDirectory;
    }
    private void checkFiles(File requestFile, File responseDirectory) throws IOException{
        if (!requestFile.isFile())
            throw new IOException("запрос не является файлом");
        if (!responseDirectory.isDirectory())
            throw new IOException("Ответ не является папкой");
        if (!requestFile.getName().endsWith("xls") || !requestFile.getName().endsWith("csv") || !requestFile.getName().endsWith("xlsx"))
            throw new IOException("Запрос не являяется csv, xls, xlsx файлом");

    }
    private void extractZip(){
        File[] zipFiles = responseDirectory.listFiles();
        ZipInputStream zipInputStream;
        ZipOutputStream zipOutputStream;
        if (zipFiles!= null){
            for(File zipFile: zipFiles){
                try {
                    zipInputStream= new ZipInputStream(new FileInputStream(zipFile));
                    zipInputStream.read();
                }
                catch(IOException e){
                    System.out.println(e);
                    }
            }
        }
    }
    private File createExctractDirectory(){
        String newDirectoryPath = this.responseDirectory.getAbsolutePath() + "new";
        File newDirectory = new File(newDirectoryPath);
        newDirectory.mkdir();
        return newDirectory;
    }
    private void delete_non_xml(){

    }
    private ArrayList getXmls(){
        return null;
    }
    private String parseXml(){
        return null;
    }
    private HashSet getResponseSet(){
        return null;
    }
    private HashSet getRequestSet(){
        return null;
    }
    private void compare(){

    }


}
