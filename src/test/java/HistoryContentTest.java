import org.example.models.HistoryContent;
import org.junit.jupiter.api.*;
import ru.sfedu.dubina.Constants;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryContentTest {

    private HistoryContent historyContent;

    @BeforeEach
    public void setUp() {
        try {
            Map<String, Object> testObject = new HashMap<>();
            testObject.put("key1", "value1");
            testObject.put("key2", "value2");

            historyContent = new HistoryContent("TestClass", "2024-12-09", "1L", "Test Method", testObject, HistoryContent.Status.SENT);
        } catch (Exception e) {
            System.err.println("Error during setUp: " + e.getMessage());
            fail("Setup failed due to exception: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            // Здесь можно добавить код для очистки или удаления данных после тестов, если это необходимо.
            System.out.println("Test cleanup completed.");
        } catch (Exception e) {
            System.err.println("Error during tearDown: " + e.getMessage());
        }
    }

    @Test
    public void testCreateHistoryContent() {
        try {
            assertNotNull(historyContent, "HistoryContent object should not be null.");
            assertEquals("TestClass", historyContent.getClassName(), "Class name should match.");
            assertEquals("1L", historyContent.getActor(), "Actor should match.");
            assertEquals("Test Method", historyContent.getMethodName(), "Method name should match.");
            assertNotNull(historyContent.getObject(), "Object map should not be null.");
            assertEquals(2, historyContent.getObject().size(), "Object map should have 2 entries.");
            assertEquals(HistoryContent.Status.SENT, historyContent.getStatus(), "Status should match SENT.");
        } catch (Exception e) {
            System.err.println("Error during testCreateHistoryContent: " + e.getMessage());
            fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateHistoryContent() {
        try {
            historyContent.setMethodName("Updated Test Method");
            historyContent.setStatus(HistoryContent.Status.FAILURE);

            assertEquals("Updated Test Method", historyContent.getMethodName(), "Updated method name should match.");
            assertEquals(HistoryContent.Status.FAILURE, historyContent.getStatus(), "Updated status should match FAILURE.");
        } catch (Exception e) {
            System.err.println("Error during testUpdateHistoryContent: " + e.getMessage());
            fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidHistoryContent() {
        try {
            HistoryContent invalidContent = new HistoryContent(null, null, null, null, null, null);
            assertNull(invalidContent.getClassName(), "Class name should be null.");
            assertNull(invalidContent.getMethodName(), "Method name should be null.");
            assertNull(invalidContent.getStatus(), "Status should be null.");
        } catch (Exception e) {
            System.err.println("Error during testInvalidHistoryContent: " + e.getMessage());
            fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testToStringMethod() {
        try {
            String result = historyContent.toString();
            assertTrue(result.contains("HistoryContent{"), "toString should include HistoryContent{");
            assertTrue(result.contains("className='TestClass'"), "toString should include className.");
        } catch (Exception e) {
            System.err.println("Error during testToStringMethod: " + e.getMessage());
            fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
