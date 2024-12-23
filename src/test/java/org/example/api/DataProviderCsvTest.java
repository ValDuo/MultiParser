package org.example.api;

import org.apache.log4j.Logger;
import org.example.models.PersForApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.dubina.Constants;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCsvTest {
    Logger logger = Logger.getLogger(DataProviderCsvTest.class);
    private DataProviderCsv dataProvider;

    @BeforeEach
    void setUp() {
        dataProvider = new DataProviderCsv() {
            @Override
            public void initDataSource() {

            }
        };

        File csvFile = new File(Constants.csvFilePath);
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

    }


    @Test
    void saveRecord() {
        PersForApi person = new PersForApi(1L, "John Doe", "john.doe@example.com");
        dataProvider.saveRecord(person);

        PersForApi retrievedPerson = dataProvider.getRecordById(1L);
        assertNotNull(retrievedPerson);
        assertEquals(person.getId(), retrievedPerson.getId());
        assertEquals(person.getName(), retrievedPerson.getName());
        assertEquals(person.getEmail(), retrievedPerson.getEmail());
    }

    @Test
    void getRecordById() {
        PersForApi person = new PersForApi(2L, "Jane Doe", "jane.doe@example.com");
        dataProvider.saveRecord(person);

        PersForApi retrievedPerson = dataProvider.getRecordById(2L);
        assertNotNull(retrievedPerson);
        assertEquals(person.getId(), retrievedPerson.getId());
        assertEquals(person.getName(), retrievedPerson.getName());
        assertEquals(person.getEmail(), retrievedPerson.getEmail());
    }

    @Test
    void deleteRecord() {
        PersForApi person = new PersForApi(3L, "Jim Doe", "jim.doe@example.com");
        dataProvider.saveRecord(person);
        assertNotNull(dataProvider.getRecordById(3L));

        dataProvider.deleteRecord(3L);
        PersForApi deletedPerson = dataProvider.getRecordById(3L);
        assertNull(deletedPerson);
    }
}
