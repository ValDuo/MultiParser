import org.junit.Test;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.yaml.snakeyaml.Yaml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static ru.sfedu.dubina.Constants.*;

public class ClientTest {
    private static Logger logger = Logger.getLogger(ClientTest.class);

    public ClientTest() {
        logger.debug("Client[0]: starting application.........");
    }

    @Test
    public void loggerTest() {
        ClientTest client = new ClientTest();
        client.logBasicSystemInfo();
    }

    @Test
    public void logBasicSystemInfo() {
        logger.info("Launching the application...");
        logger.info("Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        logger.info("JRE: " + System.getProperty("java.version"));
        logger.info("Java Launched From: " + System.getProperty("java.home"));
        logger.info("Class Path: " + System.getProperty("java.class.path"));
        logger.info("Library Path: " + System.getProperty("java.library.path"));
        logger.info("User Home Directory: " + System.getProperty("user.home"));
        logger.info("User Working Directory: " + System.getProperty("user.dir"));
        logger.info("Test INFO logging.");
    }

    public static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (FileNotFoundException e) {
            logger.error("Файл не найден: " + filePath, e);
        } catch (IOException e) {
            logger.error("Ошибка чтения файла: " + filePath, e);
        }
        return properties;
    }

    public static Map<String, Object> loadYml(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream input = new FileInputStream(filePath)) {
            return yaml.load(input);
        } catch (FileNotFoundException e) {
            logger.error("YML файл не найден: " + filePath, e);
        } catch (IOException e) {
            logger.error("Ошибка загрузки YML файла: " + filePath, e);
        }
        return new HashMap<>();
    }

    public static Object getValue(Map<String, Object> data, String... keys) {
        Map<String, Object> current = data;
        for (String key : keys) {
            current = (Map<String, Object>) current.get(key);
            if (current == null) {
                logger.error("Ключ не найден: " + key);
                return null;
            }
        }
        return current;
    }

    public static Document loadXml(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(xmlFile);
        } catch (Exception e) {
            logger.error("Ошибка загрузки XML файла: " + filePath, e);
        }
        return null;
    }

    public static List<String> getPlanets(String filePath, String format) throws Exception {
        List<String> planets = new ArrayList<>();
        switch (format.toLowerCase()) {
            case "properties":
                Properties properties = loadProperties(filePath);
                String planetsStr = properties.getProperty("planets");
                if (planetsStr != null) {
                    planets = Arrays.asList(planetsStr.split(","));
                } else {
                    logger.warn("Планеты не найдены в properties файле.");
                }
                break;

            case "yml":
                Map<String, Object> ymlData = loadYml(filePath);
                planets = (List<String>) getValue(ymlData, "planets");
                if (planets == null || planets.isEmpty()) {
                    logger.warn("Планеты не найдены в YML файле.");
                }
                break;

            case "xml":
                Document xmlDocument = loadXml(filePath);
                if (xmlDocument != null) {
                    NodeList nodeList = xmlDocument.getElementsByTagName("planet");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        planets.add(nodeList.item(i).getTextContent());
                    }
                } else {
                    logger.warn("Планеты не найдены в XML файле.");
                }
                break;

            default:
                throw new IllegalArgumentException("Неподдерживаемый формат: " + format);
        }
        return planets;
    }

    public static void main(String[] args) throws Exception {
        logger.info("Программа запущена.");

        try {
            List<String> planetsFromProperties = getPlanets(PATH_TO_PROP, "properties");
            logger.info("Планеты из properties: " + planetsFromProperties);

            List<String> planetsFromYaml = getPlanets(PATH_TO_YAML, "yml");
            logger.info("Планеты из yaml: " + planetsFromYaml);

            List<String> planetsFromXml = getPlanets(PATH_TO_XML, "xml");
            logger.info("Планеты из xml: " + planetsFromXml);

        } catch (Exception e) {
            logger.error("Ошибка на этапе вывода планет!", e);
        }

        logger.info("Выполнение завершено.");
    }
}
