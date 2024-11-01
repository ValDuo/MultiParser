import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.regex.*;
import java.util.ArrayList;

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
    public void startParse() {
        final String url = "https://egrpru.com/";
        driver.get(url);
        sendKadastrs();


//        Thread.sleep(100);
        driver.close();
    }
    private void sendKadastrs(){
        ArrayList<String> addresses = getAddresses();
        for(int i = 1 + srcDstFiles.getDistination().fileLen(); i < srcDstFiles.getSource().fileLen(); i++){
            WebElement form_input = driver.findElement(By.id("form_search"));
            WebElement button_input = driver.findElement(By.id("btn_search"));

        }
    }
    private ArrayList<String> getAddresses(){
        return null;
    }




}
