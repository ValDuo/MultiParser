import static org.junit.Assert.*;
import static ru.sfedu.dubina.utils.Constants.*;


import org.junit.Test;
import java.util.List;

public class ClientTestTest {

    @Test
    public void testGetPlanetsFromProperties() throws Exception {
        List<String> planets = ClientTest.getPlanets(PATH_TO_PROP, "properties");
        assertNotNull("Список планет не должен быть null.", planets);
        assertTrue("Список планет должен содержать хотя бы один элемент.", planets.size() > 0);
    }

    @Test
    public void testGetPlanetsFromYml() throws Exception {
        List<String> planets = ClientTest.getPlanets(PATH_TO_YAML, "yml");
        assertNotNull("Список планет не должен быть null.", planets);
        assertTrue("Список планет должен содержать хотя бы один элемент.", planets.size() > 0);
    }

    @Test
    public void testGetPlanetsFromXml() throws Exception {
        List<String> planets = ClientTest.getPlanets(PATH_TO_XML, "xml");
        assertNotNull("Список планет не должен быть null.", planets);
        assertTrue("Список планет должен содержать хотя бы один элемент.", planets.size() > 0);
    }


}
