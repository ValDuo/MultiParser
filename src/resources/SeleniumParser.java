package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

import javax.swing.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


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
    public void setSrcAndDst(CSV_IO srcFile){
        this.srcDstFiles = new ParsingSrcDstFiles(srcFile);
    }
    public void startParse() {
        final String url = "https://egrpru.com/";
        driver.get(url);
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendKadastrs();
        driver.close();
    }
    private void sendKadastrs(){
        ArrayList<String> addresses = getAddresses(0);
        for(int i = 1 + srcDstFiles.getDistination().fileLen(); i < srcDstFiles.getSource().fileLen(); i++){
            WebElement form_input = driver.findElement(By.id("form_search"));
            WebElement button_input = driver.findElement(By.id("btn_search"));
            form_input.sendKeys(addresses.get(i));
            button_input.click();
            try {
                sleep(7500);
            }
            catch (InterruptedException exception){

            }
            ArrayList<WebElement> addressesRecieved = (ArrayList<WebElement>) driver.findElements(By.className("search-result__row"));
            String result_text = checkReciviedAddress(addressesRecieved);
            if  (result_text != null)
                srcDstFiles.getDistination().writeCSVLine(result_text);
            // чистим поле ввода, clear не работает :(((((((
            form_input = driver.findElement(By.id("form_search"));
            this.clear_input(form_input, addresses.get(i));
        }

    }
    private void clear_input(WebElement input, String address){
        for(int i = 0; i < address.length(); i++)
            input.sendKeys(Keys.BACK_SPACE);
    }
    private ArrayList<String> getAddresses(int column){
        // думаю, будет уместо 2 варианта поиска адреса
        // потому что регулярка для проверки адреса займет больше времени
        // чем разработка остальной программы
        // 1) передавать в списке только адреса
        // 2) передавать в отдельном поле ввода номер столбца, в котором лежит адрес
        ArrayList<String> addresses = new ArrayList<>();
        ArrayList<String> lines = srcDstFiles.source.readLines();
        for(int i = 0; i < lines.size(); i++){
            String[] line_splitted = lines.get(i).split(";");
            addresses.add(line_splitted[column]);
        }
        return addresses;
    }
    private String checkReciviedAddress(ArrayList<WebElement> addresses){
        for(WebElement address:addresses){
            if (address.getText().contains("Жилое помещение")){
                int kadastr_index = address.getText().indexOf("Адрес объекта:") - "Кадастровый номер:".length();
                return address.getText().subSequence("Кадастровый номер:".length(),address.getText().indexOf("Адрес объекта:")).toString().strip();
            }
        }
        return null;
    }
    private ArrayList<String> addHome(ArrayList<String> addresses){
        //TODO: optional: parse address to get addreses's parts
        //TODO: like street, house, flat, etc

        return addresses;
    }

}
