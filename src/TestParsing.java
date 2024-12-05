import org.openqa.selenium.chrome.ChromeDriver;

public class TestParsing {
    public static void main(String[] args) throws Exception {
        SeleniumParser parser = new SeleniumParser(new ChromeDriver());
        parser.setSrcAndDst("../dstFiles/new_kadastr_29_10_2024.csv");
        parser.startParse();
    }
}
