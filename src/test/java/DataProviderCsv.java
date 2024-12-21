import org.example.api.DataProviderCsv;
import org.example.models.HistoryContent;
import org.junit.jupiter.api.*;
import ru.sfedu.dubina.Constants;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

class DataProviderCsvTest {

    private DataProviderCsv dataProviderCsv;

    @BeforeEach
    public void setUp() {
        dataProviderCsv = new DataProviderCsv(Constants.CSV_FILE);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(Constants.CSV_FILE);
        if (file.exists()) {
            if (!file.delete()) {
                System.err.println("Failed to delete test CSV file");
            }
        }
    }

    @Test
    public void testSaveRecord() {
        HistoryContent record = createTestHistoryContent();

        try {
            dataProviderCsv.saveRecord(record);
            HistoryContent retrieved = dataProviderCsv.getRecordById(record.getId());
            assertNotNull(retrieved, "Record should not be null.");
            assertEquals(record.getId(), retrieved.getId(), "IDs should match.");
            assertEquals(record.getClassName(), retrieved.getClassName(), "Class names should match.");
            assertEquals(record.getMethodName(), retrieved.getMethodName(), "Method names should match.");

        } catch (Exception e) {
            fail("Ошибка в ходе выполнения testSaveRecord: " + e.getMessage());
        }
    }

    @Test
    public void testGetRecordById() {
        HistoryContent record = createTestHistoryContent();

        try {
            dataProviderCsv.saveRecord(record);
            HistoryContent retrieved = dataProviderCsv.getRecordById(record.getId());
            assertNotNull(retrieved, "Record should not be null.");
            assertEquals(record.getId(), retrieved.getId(), "IDs should match.");
        } catch (Exception e) {
            fail("Ошибка в ходе выполнения testGetRecordById: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateRecord() {
        HistoryContent record = createTestHistoryContent();

        try {
            dataProviderCsv.saveRecord(record);
            record.setMethodName("Updated Test Method");
            dataProviderCsv.updateRecord(record.getId(), record);
            HistoryContent updated = dataProviderCsv.getRecordById(record.getId());
            assertNotNull(updated, "Updated record should not be null.");
            assertEquals("Updated Test Method", updated.getMethodName(), "Обновленный метод должен синхронизироваться с исходным.");

        } catch (Exception e) {
            fail("Ошибка в ходе выполнения testUpdateRecord: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteRecord() {
        HistoryContent record = createTestHistoryContent();

        try {
            dataProviderCsv.saveRecord(record);
            dataProviderCsv.deleteRecord(record.getId());

            // Проверяем, что запись была удалена
            HistoryContent deleted = dataProviderCsv.getRecordById(record.getId());
            assertNull(deleted, "Record should be null after deletion.");

        } catch (Exception e) {
            fail("Ошибка в ходе выполнения testDeleteRecord: " + e.getMessage());
        }
    }

    private HistoryContent createTestHistoryContent() {
        Map<String, Object> object = new HashMap<>();
        object.put("key", "value");
        return new HistoryContent("TestClass", "2024-12-09", "1L", "Test Method", object, HistoryContent.Status.SENT);
    }
}
