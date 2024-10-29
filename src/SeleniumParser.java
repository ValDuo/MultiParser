import org.openqa.selenium.WebDriver;

public class SeleniumParser {
    WebDriver driver;
    ParsingSrcDstFiles srcDstFiles;
    SeleniumParser(WebDriver driver){
        this.driver = driver;
    }
    public void setSrcAndDst(String srcPath){
         this.srcDstFiles= new ParsingSrcDstFiles(srcPath);
    }
    public void setSrcAndDst(String srcPath, String dstPath){
        this.srcDstFiles = new ParsingSrcDstFiles(srcPath,dstPath);
    }
    public void startParse() throws InterruptedException {
        final String url = "https://egrpru.com/";
        driver.get(url);
        Thread.sleep(100);
        driver.close();
    }


}
