import java.io.IOException;

public class FilterThread extends Thread{
    private boolean isActive;
    private String responsePath;
    private String requestPath;
    void disable(){
        isActive= false;
    }
    FilterThread(String requestPath, String responsePath){
        isActive = true;
        this.requestPath = requestPath;
        this.responsePath = responsePath;

    }
    @Override
    public void run() {// Этот метод будет вызван при старте потока
        try {
            ComparatorSentRecieved comparator = new ComparatorSentRecieved(requestPath, responsePath);
            comparator.compare();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


