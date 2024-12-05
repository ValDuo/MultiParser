import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProcessThread extends Thread{
    private boolean isActive;
    private CSV_IO csv;
    private WebDriver driver;
    void disable(){
        isActive= false;
    }
    ProcessThread(CSV_IO srcFile, WebDriver driver){
        super();
        isActive = true;
        this.csv = srcFile;
        this.driver = driver;
    }

    @Override
    public void run() {// Этот метод будет вызван при старте потока
        SeleniumParser parser = new SeleniumParser(driver);
        parser.setSrcAndDst(csv);
        parser.startParse();
//        this.disable();
    }
}


