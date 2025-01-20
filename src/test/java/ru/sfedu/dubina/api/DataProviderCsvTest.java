package ru.sfedu.dubina.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.sfedu.dubina.models.*;
import ru.sfedu.dubina.utils.Constants;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.dubina.api.DataProviderCSV.dateFormat;

public class DataProviderCsvTest {

    private DataProviderCSV dataProvider;

    @BeforeEach
    public void setUpBigCsvCutter() {
        dataProvider = new DataProviderCSV();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.CUTTER_PATH))) {
            writer.write("id,countLine,countFile,date,folder,created\n"); // Заголовок
        } catch (IOException e) {
            fail("Ошибка при очистке тестового файла: " + e.getMessage());
        }
    }

    @Test
    public void testCreateBigCSVCutter() {
        BigCSVCutter cutter = new BigCSVCutter(100, 10, "2025-01-20", "test_folder", true);
        boolean result = dataProvider.createBigCSVCutter(cutter);
        assertTrue(result, "Запись должна быть успешно создана");

        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.CUTTER_PATH))) {
            long count = reader.lines().count();
            assertEquals(2, count, "Файл должен содержать 2 строки (заголовок и запись)");
        } catch (IOException e) {
            fail("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    @Test
    public void testGetBigCSVCutterById() {
        BigCSVCutter cutter = new BigCSVCutter(100, 10, "2025-01-20", "test_folder", true);
        dataProvider.createBigCSVCutter(cutter);

        BigCSVCutter retrieved = dataProvider.getBigCSVCutterById(cutter.getId());
        assertNotNull(retrieved, "Должен вернуть существующую запись");
        assertEquals(cutter.getCountLine(), retrieved.getCountLine(), "Количество строк должно совпадать");
        assertEquals(cutter.getFolder(), retrieved.getFolder(), "Имя папки должно совпадать");
    }

    @Test
    public void testUpdateBigCSVCutter() {
        BigCSVCutter cutter = new BigCSVCutter(100, 10, "2025-01-20", "test_folder", true);
        dataProvider.createBigCSVCutter(cutter);

        BigCSVCutter updatedCutter = new BigCSVCutter(200, 20, "2025-01-21", "updated_folder", false);
        boolean result = dataProvider.updateBigCSVCutter(cutter.getId(), updatedCutter);
        assertTrue(result, "Запись должна быть успешно обновлена");

        BigCSVCutter retrieved = dataProvider.getBigCSVCutterById(cutter.getId());
        assertNotNull(retrieved, "Должен вернуть существующую запись");
        assertEquals(updatedCutter.getCountLine(), retrieved.getCountLine(), "Количество строк должно быть обновлено");
        assertEquals(updatedCutter.getFolder(), retrieved.getFolder(), "Имя папки должно быть обновлено");
    }

    @Test
    public void testDeleteBigCSVCutter() {
        BigCSVCutter cutter = new BigCSVCutter(100, 10, "2025-01-20", "test_folder", true);
        dataProvider.createBigCSVCutter(cutter);

        boolean result = dataProvider.deleteBigCSVCutter(cutter.getId());
        assertTrue(result, "Запись должна быть успешно удалена");

        BigCSVCutter retrieved = dataProvider.getBigCSVCutterById(cutter.getId());
        assertNull(retrieved, "Запись не должна существовать после удаления");
    }

    @Test
    public void testDeleteNonExistentBigCSVCutter() {
        UUID randomId = UUID.randomUUID();
        boolean result = dataProvider.deleteBigCSVCutter(randomId);
        assertFalse(result, "Нельзя удалить несуществующую запись");
    }

    @Test
    public void testGetNonExistentBigCSVCutter() {
        UUID randomId = UUID.randomUUID();
        BigCSVCutter retrieved = dataProvider.getBigCSVCutterById(randomId);
        assertNull(retrieved, "Не должно возвращать запись для несуществующего ID");
    }

    //тесты для csvReader

    @BeforeEach
    public void setUpCSVReader() throws IOException {
        dataProvider = new DataProviderCSV();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.READER_PATH))) {
            writer.write("id,pathName,csvFile,words\n");
        } catch (IOException e) {
            fail("Ошибка при очистке тестового файла: " + e.getMessage());
        }
    }
    @Test
    public void testCreateCSVReader() {
        CSVReader csvReader = new CSVReader("path/to/file", "example.csv", 100);
        boolean isCreated = dataProvider.createCSVReader(csvReader);

        assertTrue(isCreated, "Запись должна быть успешно создана");

        CSVReader retrieved = dataProvider.getCSVReaderById(csvReader.getId());
        assertNotNull(retrieved, "Созданная запись должна быть найдена");
        assertEquals(csvReader.getPathName(), retrieved.getPathName());
        assertEquals(csvReader.getCsvFile(), retrieved.getCsvFile());
        assertEquals(csvReader.getWords(), retrieved.getWords());
    }

    @Test
    public void testGetCSVReaderById() {
        CSVReader csvReader = new CSVReader("path/to/file", "example.csv", 100);
        dataProvider.createCSVReader(csvReader);

        CSVReader retrieved = dataProvider.getCSVReaderById(csvReader.getId());
        assertNotNull(retrieved, "Запись должна быть найдена");
        assertEquals(csvReader.getPathName(), retrieved.getPathName());
        assertEquals(csvReader.getCsvFile(), retrieved.getCsvFile());
        assertEquals(csvReader.getWords(), retrieved.getWords());
    }

    @Test
    public void testUpdateCSVReader() {
        CSVReader csvReader = new CSVReader("path/to/file", "example.csv", 100);
        dataProvider.createCSVReader(csvReader);

        CSVReader updatedReader = new CSVReader("new/path", "new_example.csv", 200);
        boolean isUpdated = dataProvider.updateCSVReader(csvReader.getId(), updatedReader);

        assertTrue(isUpdated, "Запись должна быть успешно обновлена");

        CSVReader retrieved = dataProvider.getCSVReaderById(csvReader.getId());
        assertNotNull(retrieved, "Обновлённая запись должна быть найдена");
        assertEquals(updatedReader.getPathName(), retrieved.getPathName());
        assertEquals(updatedReader.getCsvFile(), retrieved.getCsvFile());
        assertEquals(updatedReader.getWords(), retrieved.getWords());
    }

    @Test
    public void testDeleteCSVReader() {
        CSVReader csvReader = new CSVReader("path/to/file", "example.csv", 100);
        dataProvider.createCSVReader(csvReader);

        boolean isDeleted = dataProvider.deleteCSVReader(csvReader.getId());
        assertTrue(isDeleted, "Запись должна быть успешно удалена");

        CSVReader retrieved = dataProvider.getCSVReaderById(csvReader.getId());
        assertNull(retrieved, "Удалённая запись не должна быть найдена");
    }

    @Test
    public void testGetCSVReaderById_NotFound() {
        UUID randomId = UUID.randomUUID();
        CSVReader retrieved = dataProvider.getCSVReaderById(randomId);
        assertNull(retrieved, "Запись с случайным ID не должна быть найдена");
    }

    //тесты для incomingemails

    @BeforeEach
    public void setUpIncomingEmails() throws IOException {
        dataProvider = new DataProviderCSV();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.EMAILS_PATH))) {
            writer.write("id,emailAddress,emailSender,emailReceiver\n");
        } catch (IOException e) {
            fail("Ошибка при очистке тестового файла: " + e.getMessage());
        }
    }

    @Test
    public void testCreateIncomingEmail() {
        IncomingEmails email = new IncomingEmails("test@example.com", "sender@example.com", "receiver@example.com");
        boolean isCreated = dataProvider.createIncomingEmail(email);

        assertTrue(isCreated, "Запись должна быть успешно создана");

        IncomingEmails retrieved = dataProvider.getIncomingEmailById(email.getId());
        assertNotNull(retrieved, "Созданная запись должна быть найдена");
        assertEquals(email.getEmailAddress(), retrieved.getEmailAddress());
        assertEquals(email.getEmailSender(), retrieved.getEmailSender());
        assertEquals(email.getEmailReceiver(), retrieved.getEmailReceiver());
    }

    @Test
    public void testGetIncomingEmailById() {
        IncomingEmails email = new IncomingEmails("test@example.com", "sender@example.com", "receiver@example.com");
        dataProvider.createIncomingEmail(email);

        IncomingEmails retrieved = dataProvider.getIncomingEmailById(email.getId());
        assertNotNull(retrieved, "Запись должна быть найдена");
        assertEquals(email.getEmailAddress(), retrieved.getEmailAddress());
        assertEquals(email.getEmailSender(), retrieved.getEmailSender());
        assertEquals(email.getEmailReceiver(), retrieved.getEmailReceiver());
    }

    @Test
    public void testUpdateIncomingEmail() {
        IncomingEmails email = new IncomingEmails("test@example.com", "sender@example.com", "receiver@example.com");
        dataProvider.createIncomingEmail(email);

        IncomingEmails updatedEmail = new IncomingEmails("new@example.com", "new_sender@example.com", "new_receiver@example.com");
        boolean isUpdated = dataProvider.updateIncomingEmail(email.getId(), updatedEmail);

        assertTrue(isUpdated, "Запись должна быть успешно обновлена");

        IncomingEmails retrieved = dataProvider.getIncomingEmailById(email.getId());
        assertNotNull(retrieved, "Обновлённая запись должна быть найдена");
        assertEquals(updatedEmail.getEmailAddress(), retrieved.getEmailAddress());
        assertEquals(updatedEmail.getEmailSender(), retrieved.getEmailSender());
        assertEquals(updatedEmail.getEmailReceiver(), retrieved.getEmailReceiver());
    }

    @Test
    public void testDeleteIncomingEmail() {
        IncomingEmails email = new IncomingEmails("test@example.com", "sender@example.com", "receiver@example.com");
        dataProvider.createIncomingEmail(email);

        boolean isDeleted = dataProvider.deleteIncomingEmail(email.getId());
        assertTrue(isDeleted, "Запись должна быть успешно удалена");

        IncomingEmails retrieved = dataProvider.getIncomingEmailById(email.getId());
        assertNull(retrieved, "Удалённая запись не должна быть найдена");
    }

    @Test
    public void testGetIncomingEmailById_NotFound() {
        UUID randomId = UUID.randomUUID();
        IncomingEmails retrieved = dataProvider.getIncomingEmailById(randomId);
        assertNull(retrieved, "Запись с случайным ID не должна быть найдена");
    }
    //тесты для seleniumParser
    @BeforeEach
    public void setUpSeleniumParser() {
        dataProvider = new DataProviderCSV();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PARCER_PATH))) {
            writer.write("id,driver,srcDstFiles\n");
        } catch (IOException e) {
            fail("Ошибка при очистке тестового файла: " + e.getMessage());
        }
    }

    @Test
    public void testCreateSeleniumParser() {
        SeleniumParser parser = new SeleniumParser(5, "src_to_dst");
        boolean result = dataProvider.createSeleniumParser(parser);
        assertTrue(result, "Запись должна быть успешно создана");

        SeleniumParser retrieved = dataProvider.getSeleniumParserById(parser.getId());
        assertNotNull(retrieved, "Созданная запись должна быть найдена");
        assertEquals(parser.getDriver(), retrieved.getDriver());
        assertEquals(parser.getSrcDstFiles(), retrieved.getSrcDstFiles());
    }

    @Test
    public void testGetSeleniumParserById() {
        SeleniumParser parser = new SeleniumParser(5, "src_to_dst");
        dataProvider.createSeleniumParser(parser);

        SeleniumParser retrieved = dataProvider.getSeleniumParserById(parser.getId());
        assertNotNull(retrieved, "Запись должна быть найдена");
        assertEquals(parser.getDriver(), retrieved.getDriver());
        assertEquals(parser.getSrcDstFiles(), retrieved.getSrcDstFiles());
    }

    @Test
    public void testUpdateSeleniumParser() {
        SeleniumParser parser = new SeleniumParser(5, "src_to_dst");
        dataProvider.createSeleniumParser(parser);

        SeleniumParser updatedParser = new SeleniumParser(10, "updated_src_to_dst");
        boolean result = dataProvider.updateSeleniumParser(parser.getId(), updatedParser);
        assertTrue(result, "Запись должна быть успешно обновлена");

        SeleniumParser retrieved = dataProvider.getSeleniumParserById(parser.getId());
        assertNotNull(retrieved, "Обновлённая запись должна быть найдена");
        assertEquals(updatedParser.getDriver(), retrieved.getDriver());
        assertEquals(updatedParser.getSrcDstFiles(), retrieved.getSrcDstFiles());
    }

    @Test
    public void testDeleteSeleniumParser() {
        SeleniumParser parser = new SeleniumParser(5, "src_to_dst");
        dataProvider.createSeleniumParser(parser);

        boolean result = dataProvider.deleteSeleniumParser(parser.getId());
        assertTrue(result, "Запись должна быть успешно удалена");

        SeleniumParser retrieved = dataProvider.getSeleniumParserById(parser.getId());
        assertNull(retrieved, "Удалённая запись не должна быть найдена");
    }

    @Test
    public void testGetSeleniumParserById_NotFound() {
        UUID randomId = UUID.randomUUID();
        SeleniumParser retrieved = dataProvider.getSeleniumParserById(randomId);
        assertNull(retrieved, "Запись с случайным ID не должна быть найдена");
    }

    //тесты для WithKadastrs

    @BeforeEach
    public void setUpKadastrs() throws IOException {
        dataProvider = new DataProviderCSV();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.KADASTRS_PATH))) {
            writer.write("id,source,destination,createdTime\n");
        }
    }

    @Test
    public void testCreateWithKadastrList() {
        WithKadastrList record = new WithKadastrList("source1", "destination1", new Date());
        boolean isCreated = dataProvider.createWithKadastrList(record);

        assertTrue(isCreated, "Запись должна быть успешно создана");

        WithKadastrList retrieved = dataProvider.getWithKadastrListById(record.getId());
        assertNotNull(retrieved, "Созданная запись должна быть найдена");
        assertEquals(record.getSource(), retrieved.getSource());
        assertEquals(record.getDestination(), retrieved.getDestination());
        assertEquals(dateFormat.format(record.getCreatedTime()), dateFormat.format(retrieved.getCreatedTime()));
    }

    @Test
    public void testGetWithKadastrListById() {
        WithKadastrList record = new WithKadastrList("source2", "destination2", new Date());
        dataProvider.createWithKadastrList(record);

        WithKadastrList retrieved = dataProvider.getWithKadastrListById(record.getId());
        assertNotNull(retrieved, "Запись должна быть найдена");
        assertEquals(record.getSource(), retrieved.getSource());
        assertEquals(record.getDestination(), retrieved.getDestination());
        assertEquals(dateFormat.format(record.getCreatedTime()), dateFormat.format(retrieved.getCreatedTime()));
    }

    @Test
    public void testUpdateWithKadastrList() {
        WithKadastrList record = new WithKadastrList("source3", "destination3", new Date());
        dataProvider.createWithKadastrList(record);

        WithKadastrList updatedRecord = new WithKadastrList("newSource3", "newDestination3", new Date());
        boolean isUpdated = dataProvider.updateWithKadastrList(record.getId(), updatedRecord);

        assertTrue(isUpdated, "Запись должна быть успешно обновлена");

        WithKadastrList retrieved = dataProvider.getWithKadastrListById(record.getId());
        assertNotNull(retrieved, "Обновлённая запись должна быть найдена");
        assertEquals(updatedRecord.getSource(), retrieved.getSource());
        assertEquals(updatedRecord.getDestination(), retrieved.getDestination());
    }

    @Test
    public void testDeleteWithKadastrList() {
        WithKadastrList record = new WithKadastrList("source4", "destination4", new Date());
        dataProvider.createWithKadastrList(record);

        boolean isDeleted = dataProvider.deleteWithKadastrList(record.getId());
        assertTrue(isDeleted, "Запись должна быть успешно удалена");

        WithKadastrList retrieved = dataProvider.getWithKadastrListById(record.getId());
        assertNull(retrieved, "Удалённая запись не должна быть найдена");
    }

    @Test
    public void testGetWithKadastrListById_NotFound() {
        UUID randomId = UUID.randomUUID();
        WithKadastrList retrieved = dataProvider.getWithKadastrListById(randomId);
        assertNull(retrieved, "Запись с случайным ID не должна быть найдена");
    }

}



