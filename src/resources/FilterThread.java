package resources;

import java.io.IOException;
import java.io.File;
public class FilterThread extends Thread{
    private boolean isActive;
    private String responsePath;
    private String requestPath;
    private File[] requestFiles;
    void disable(){
        isActive= false;
    }

    FilterThread(String requestPath, String responsePath){
        isActive = true;
        this.requestPath = requestPath;
        this.responsePath = responsePath;

    }
    FilterThread(File[] requestFiles, String responsePath){
        isActive = true;
        this.requestFiles = requestFiles;
        this.responsePath = responsePath;

    }


    @Override
    public void run() {// Этот метод будет вызван при старте потока
        try {
            ComparatorSentRecieved comparator;
            if (this.requestFiles == null) {
                comparator = new ComparatorSentRecieved(requestPath, responsePath);
            }
            else{
                comparator = new ComparatorSentRecieved(requestFiles, responsePath);
            }
            comparator.compare();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


