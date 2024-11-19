import org.openqa.selenium.chrome.ChromeDriver;

public class ProcessThread extends Thread{
    private boolean isActive;
    private CSVReader csv;
    void disable(){
        isActive= false;
    }
    ProcessThread(CSVReader srcFile){
        super();
        isActive = true;
        this.csv = srcFile;
    }
    @Override
    public void run() {// Этот метод будет вызван при старте потока
        SeleniumParser parser = new SeleniumParser(new ChromeDriver());
        parser.setSrcAndDst(csv);
        parser.startParse();
//        this.disable();
    }
}


