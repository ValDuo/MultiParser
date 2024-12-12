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
    private HashSet<String> requestSet;
    private HashSet<String> responseSet;
    private File requestFile;
    private File[] requestFiles;
    private File responseDirectory;
    private File extractingDir;
    private String extractingDirPath;
    private Double processPercent = 0d;
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
    ComparatorSentRecieved(File[] requestFiles, String responseDirectoryPath) throws IOException{
        for(File file:requestFiles)
            checkFiles(file, new File(responseDirectoryPath));
        this.requestFiles = requestFiles;
        this.responseDirectory = new File(responseDirectoryPath);
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
        if (zipFiles!= null){
            int zipFilesLength = zipFiles.length;
            double step = zipFilesLength/100.0;
            step = step/2;
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
                    setProcessBar(step);
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
    private HashSet<String> getRequestSet(File requestFile){
        HashSet<String> requestSet = new HashSet<>();
        CSV_IO csv = new CSV_IO(requestFile.getAbsolutePath());
        ArrayList<String> addresses = csv.readLines();
        for (String address:addresses){
            requestSet.add(address.split(";")[1].strip());
        }
        this.requestSet = requestSet;
        return requestSet;
    }
    private HashSet<String> getRequestSet(File[] requestFiles){
        HashSet<String> requestSet = new HashSet<>();
        for(int i = 0; i < requestFiles.length; i++) {
            HashSet<String> oneFileSet = getRequestSet(requestFiles[i]);
            requestSet.addAll(oneFileSet);
        }
        this.requestSet = requestSet;
        return requestSet;
    }
    public void compare(){
         extractZip();
         delete_non_xml();
         if (this.requestFiles != null)
             getRequestSet(requestFiles);
         else
             getRequestSet(requestFile);
         getResponseSet();
         HashSet<String> difference = new HashSet<>();
             for (String request_address : this.requestSet) {
                 if (!this.responseSet.contains(request_address)) {
                     difference.add(request_address);
                 }
             }
        saveCompared(difference);
    }


    private void saveCompared(HashSet<String> difference) {
        CSV_IO difference_csv = new CSV_IO(extractingDirPath+".csv");
        try {
            if(difference_csv.exists()){
                difference_csv.delete();
            }
            difference_csv.createNewFile();
        }
        catch(Exception e){
            System.out.println(e);
        }
        ArrayList<String> difference_array = new ArrayList<>(difference);
        double remainingPercent = 100 - this.processPercent;
        double remainingStep = remainingPercent/difference_array.size();
        for(String address:difference_array){
            difference_csv.writeCSVLine(address);
            setProcessBar(remainingStep);
        }
        System.out.println("Все сохранено в файл " + extractingDirPath+".csv");
    }
    private void setProcessBar(double step){
        WindowApp windowApp = new WindowApp();
        this.processPercent += step;
    }
}
