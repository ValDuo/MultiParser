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
    private void startParse(){

    }
}
