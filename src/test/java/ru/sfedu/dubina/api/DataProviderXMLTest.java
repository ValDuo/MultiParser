//package ru.sfedu.dubina.api;
//
//import org.apache.log4j.Logger;
//import ru.sfedu.dubina.models.PersForApi;
//import org.junit.jupiter.api.*;
//import ru.sfedu.dubina.utils.Constants;
//
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import static org.junit.jupiter.api.Assertions.*;
//
//class DataProviderXMLTest {
//    Logger logger = Logger.getLogger(DataProviderXMLTest.class);
//    private DataProviderXML dataProvider;
//    private File tempXmlFile;
//
//    @BeforeEach
//    void setUp() {
//        try {
//            tempXmlFile = Files.createTempFile(Paths.get(Constants.xmlFilePath).getParent(), "test_", ".xml").toFile();
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//        Constants.xmlFilePath = tempXmlFile.getAbsolutePath();
//        dataProvider = new DataProviderXML() {
//            @Override
//            public void initDataSource() {
//                super.initDataSource();
//            }
//        };
//        dataProvider.initDataSource();
//    }
//
//    @Test
//    void saveRecord() {
//        PersForApi person = new PersForApi(1L, "John Doe", "john.doe@example.com");
//        dataProvider.saveRecord(person);
//        PersForApi retrievedPerson = dataProvider.getRecordById(1L);
//        assertNotNull(retrievedPerson);
//        assertEquals(person.getId(), retrievedPerson.getId());
//        assertEquals(person.getName(), retrievedPerson.getName());
//        assertEquals(person.getEmail(), retrievedPerson.getEmail());
//    }
//
//    @Test
//    void getRecordById_notFound() {
//        PersForApi retrievedPerson = dataProvider.getRecordById(999L);
//        assertNull(retrievedPerson);
//    }
//
//    @Test
//    void deleteRecord() {
//        PersForApi person = new PersForApi(2L, "Jane Doe", "jane.doe@example.com");
//        dataProvider.saveRecord(person);
//        PersForApi retrievedPerson = dataProvider.getRecordById(2L);
//        assertNotNull(retrievedPerson);
//        dataProvider.deleteRecord(2L);
//        PersForApi deletedPerson = dataProvider.getRecordById(2L);
//        assertNull(deletedPerson);
//    }
//
//
//}
