package ru.sfedu.dubina.api;

import org.junit.jupiter.api.*;
import ru.sfedu.dubina.models.BigCSVCutter;
import ru.sfedu.dubina.models.CSVReader;
import ru.sfedu.dubina.utils.Constants;

import java.io.FileWriter;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class DataProviderXMLTest {

    private DataProviderXML dataProvider= new DataProviderXML();


    // Тесты для BigCSVCutter

    @Test
    public void testCreateBigCSVCutter() {
        BigCSVCutter cutter = new BigCSVCutter(100, 5, "2025-01-20", "folder1", true);
        dataProvider.create(cutter);

        BigCSVCutter result = dataProvider.read(cutter.getId());
        assertNotNull(result);
        assertEquals(cutter.getCountFile(), result.getCountFile());
        assertEquals(cutter.getCountLine(), result.getCountLine());
        assertEquals(cutter.getDate(), result.getDate());
        assertEquals(cutter.getFolder(), result.getFolder());
        assertEquals(cutter.getCreated(), result.getCreated());
    }

    @Test
    public void testUpdateBigCSVCutter() {
            BigCSVCutter cutter = new BigCSVCutter(100, 5, "2025-01-20", "folder1", true);
            dataProvider.create(cutter);

            BigCSVCutter updatedCutter = new BigCSVCutter(200, 10, "2025-01-21", "folder2", false);
            dataProvider.update(cutter.getId(), updatedCutter);

            BigCSVCutter result = dataProvider.read(cutter.getId());
            assertNotNull(result);
            assertEquals(updatedCutter.getCountLine(), result.getCountLine());
            assertEquals(updatedCutter.getCountFile(), result.getCountFile());
            assertEquals(updatedCutter.getDate(), result.getDate());
            assertEquals(updatedCutter.getFolder(), result.getFolder());
            assertEquals(updatedCutter.getCreated(), result.getCreated());
    }

    @Test
    public void testDeleteBigCSVCutter() {
        BigCSVCutter cutter = new BigCSVCutter(100, 5, "2025-01-20", "folder1", true);
        dataProvider.create(cutter);

        dataProvider.delete(cutter.getId());

        BigCSVCutter result = dataProvider.read(cutter.getId());
        assertNull(result);
    }


    // Тесты для CSVReader
чь
    @Test
    public void testCreateCSVReader() {
        CSVReader reader = new CSVReader("path111/to/file", "file.csv", 1000);
        dataProvider.createCSVReader(reader);

        CSVReader result = dataProvider.readCSVReader(reader.getId());
        assertNotNull(result);
        assertEquals(reader.getPathName(), result.getPathName());
        assertEquals(reader.getCsvFile(), result.getCsvFile());
        assertEquals(reader.getWords(), result.getWords());
    }
    @Test
    public void testUpdateCSVReader() {
        CSVReader reader = new CSVReader("path/to/file", "file.csv", 1000);
        dataProvider.createCSVReader(reader);

        CSVReader updatedReader = new CSVReader("path/updated/file", "updated_file.csv", 2000);
        dataProvider.updateCSVReader(reader.getId(), updatedReader);

        CSVReader result = dataProvider.readCSVReader(reader.getId());
        assertNotNull(result);
        assertEquals(updatedReader.getPathName(), result.getPathName());
        assertEquals(updatedReader.getCsvFile(), result.getCsvFile());
        assertEquals(updatedReader.getWords(), result.getWords());
    }

    @Test
    public void testDeleteCSVReader() {
        CSVReader reader = new CSVReader("path/to/file", "file.csv", 1000);
        dataProvider.createCSVReader(reader);

        dataProvider.deleteCSVReader(reader.getId());

        CSVReader result = dataProvider.readCSVReader(reader.getId());
        assertNull(result);
    }
}