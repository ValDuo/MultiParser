import org.openqa.selenium.WebDriver;

public class ProcessThread extends Thread{
    private final CSV_IO csv;
    private final WebDriver driver;
    ProcessThread(CSV_IO srcFile, WebDriver driver){
        super();
        this.csv = srcFile;
        this.driver = driver;
    }

    @Override
    public void run() {// Этот метод будет вызван при старте потока
        SeleniumParser parser = new SeleniumParser(driver);
        parser.setSrcAndDst(csv);
        parser.startParse();
    }
}


