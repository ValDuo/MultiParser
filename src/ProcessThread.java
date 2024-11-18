import org.openqa.selenium.chrome.ChromeDriver;

public class ProcessThread extends Thread{
    private boolean isActive;
    void disable(){
        isActive= false;
    }
    ProcessThread(){
        isActive = true;
    }
    @Override
    public void run() {// Этот метод будет вызван при старте потока
        SeleniumParser parser = new SeleniumParser(new ChromeDriver());
        parser.setSrcAndDst();
    }
}


