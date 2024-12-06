import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.zip.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class ComparatorSentRecieved {
    private HashSet requestSet;
    private HashSet responseSet;
    private File requestFile;
    private File responseDirectory;
    private File extractingDir;
    private String extractingDirPath;
    ComparatorSentRecieved(String requestFilePath, String responseDirectoryPath) throws IOException{
        File request = new File(requestFilePath);
        File response = new File(responseDirectoryPath);
        checkFiles(request, response);
        this.requestFile = request;
        this.responseDirectory = response;
        this.extractingDir = createExctractDirectory();
    }
    ComparatorSentRecieved(File requestFile, File responseDirectory)throws IOException{
        checkFiles(requestFile, responseDirectory);
        this.requestFile = requestFile;
        this.responseDirectory = responseDirectory;
        this.extractingDir = createExctractDirectory();
    }
    private void checkFiles(File requestFile, File responseDirectory) throws IOException{
        if (!requestFile.isFile())
            throw new IOException("запрос не является файлом");
        if (!responseDirectory.isDirectory())
            throw new IOException("Ответ не является папкой");
        if (!(requestFile.getName().endsWith("xls") || requestFile.getName().endsWith("csv") || requestFile.getName().endsWith("xlsx")))
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
                    ZipEntry zip;
                    while((zip = zipInputStream.getNextEntry())!=null){
                        String zipName = zip.getName();
                        FileOutputStream zipUnpacking = new FileOutputStream(this.extractingDir+"\\"+zipName);
                        for (int symbol = zipInputStream.read(); symbol!=-1; symbol = zipInputStream.read())
                            zipUnpacking.write(symbol);
                        zipUnpacking.flush();
                        zipInputStream.closeEntry();
                        zipUnpacking.close();
                    }
                }
                catch(IOException e){
                    System.out.println(e);
                    }
            }
        }
    }
    private File createExctractDirectory(){
        extractingDirPath = this.responseDirectory.getAbsolutePath() + "new";
        File newDirectory = new File(extractingDirPath);
        newDirectory.mkdir();
        return newDirectory;
    }
    private void delete_non_xml(){
        File[] files = this.extractingDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().endsWith("xml")){
                    file.delete();
                }
            }
        }
    }
    private ArrayList<File> getXmls(){
        String[] paths = this.extractingDir.list( (list, x) -> x.endsWith("xml"));
        ArrayList<File> xmls= new ArrayList<>();
        for(String path:paths){
            xmls.add(new File(path));
        }
        return xmls;
    }
    public String parseXml(File xml){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try{
            builder = builderFactory.newDocumentBuilder();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
        Document document = null;
        try {
            String absolute_path = xml.getCanonicalPath();
            document = builder.parse(new File(extractingDirPath +"/"+xml.getName()));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        NodeList kadastrs = document.getElementsByTagName("cad_number");
        return kadastrs.item(0).getTextContent();



    }
    public HashSet<String> getResponseSet(){
        HashSet<String> responseSet = new HashSet<>();
        ArrayList<File> xmls = getXmls();
        for(File file:xmls){
            //System.out.println(parseXml(file));
            responseSet.add(parseXml(file));
        }
        this.responseSet = responseSet;
        return responseSet;
    }
    private HashSet<String> getRequestSet(){
        return null;
    }
    public void compare(){
        //extractZip();
        //delete_non_xml();
        System.out.println(getXmls());
    }


}
