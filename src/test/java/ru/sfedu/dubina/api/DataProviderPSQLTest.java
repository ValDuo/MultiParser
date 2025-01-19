package ru.sfedu.dubina.api;


import java.io.*;
import java.sql.*;

import org.junit.jupiter.api.*;
import ru.sfedu.dubina.models.*;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderPSQLTest {
    private DataProviderPostgres dataProviderPSQL;
    @BeforeEach
    void setUp() throws SQLException, IOException {
        Connection connection = DataProviderPostgres.getConnection();
        dataProviderPSQL = new DataProviderPostgres();
    }

    @Test
    public void shouldGetJdbcConnection() throws SQLException, IOException {
        try(Connection connection = DataProviderPostgres.getConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }

    @Test
    void testCRUDMethodsWithBigCSVCutter() throws SQLException {
        BigCSVCutter bigCSVCutter = new BigCSVCutter(50,2, "2002/11/10", "src/test/testFolder", true);
        boolean createResult = dataProviderPSQL.createBigCSVCutter(bigCSVCutter);
        assertTrue(createResult, "Ошибка при создании записи.");

        BigCSVCutter retrievedUser = dataProviderPSQL.getBigCSVCutterById(bigCSVCutter.getId());
        assertEquals(50, retrievedUser.getCountLine(), "Неверное значение countline");
        assertEquals(2, retrievedUser.getCountFile(), "Неверное значение countfile");
        assertEquals("2002/11/10", retrievedUser.getDate(), "Неверное значение date");
        assertEquals("src/test/testFolder", retrievedUser.getFolder(), "Неверное значение folder");
        assertEquals(true, retrievedUser.getCreated(), "Неверное значение created");

        retrievedUser.setCountLine(100);
        retrievedUser.setCreated(false);
        retrievedUser.setCountFile(1);
        boolean updateResult = dataProviderPSQL.updateBigCSVCutter(bigCSVCutter.getId(), retrievedUser);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult = dataProviderPSQL.deleteBigCSVCutter(bigCSVCutter.getId());
        assertTrue(deleteResult, "Ошибка при удалении записи.");
    }

    @Test
    void testCRUDMethodsWithCSVReader() throws SQLException {
        CSVReader csvReader = new CSVReader("C:\\src\\test\\updatedFolder", "updatedTest.csv", 15);
        boolean createResult = dataProviderPSQL.createCSVReader(csvReader);
        assertTrue(createResult, "Ошибка при создании записи.");

        CSVReader retrievedUser = dataProviderPSQL.getCSVReaderById(csvReader.getId());
        assertNotNull(retrievedUser);
        assertEquals("C:\\src\\test\\updatedFolder", retrievedUser.getPathName());
        assertEquals("updatedTest.csv", retrievedUser.getCsvFile());
        assertEquals(15, retrievedUser.getWords());

        retrievedUser.setWords(100);
        retrievedUser.setCsvFile("c:/Lera/newDirectory/");
        retrievedUser.setCsvFile("newFile.csv");
        boolean updateResult = dataProviderPSQL.updateCSVReader(csvReader.getId(), retrievedUser);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult= dataProviderPSQL.deleteCSVReader(csvReader.getId());
        assertTrue(deleteResult, "Ошибка при удалении записи.");
    }

    @Test
    void testCRUDMethodsWithDebtorList() throws SQLException {

        DebtorList debtor = new DebtorList("Lera Doe", "Lera Smith", 100000L, 3000L, new Date(2000,12, 12), new Date(2000, 12, 13), true);
        boolean createResult = dataProviderPSQL.createDebtorList(debtor);
        assertTrue(createResult, "Ошибка при создании записи.");

        DebtorList retrievedDebtor = dataProviderPSQL.getDebtorListById(debtor.getId());
        assertNotNull(retrievedDebtor, "Запись не найдена.");
        assertEquals("Lera Doe", retrievedDebtor.getOwnerName(), "Неверное значение ownerName.");
        assertEquals("Lera Smith", retrievedDebtor.getPayerName(), "Неверное значение payerName.");
        assertEquals(100000L, retrievedDebtor.getAccruedMoney(), "Неверное значение accruedMoney.");
        assertEquals(3000L, retrievedDebtor.getReturnedMoney(), "Неверное значение returnedMoney.");
        assertNotNull(retrievedDebtor.getCreateDateOfPayment(), "Неверное значение createDateOfPayment.");
        assertNotNull(retrievedDebtor.getUploadDateOfPayment(), "Неверное значение uploadDateOfPayment.");
        assertTrue(retrievedDebtor.getKadastr(), "Неверное значение kadastr.");

        retrievedDebtor.setAccruedMoney(6000L);
        retrievedDebtor.setReturnedMoney(4000L);
        retrievedDebtor.setKadastr(false);
        boolean updateResult = dataProviderPSQL.updateDebtorList(debtor.getId(), retrievedDebtor);
        assertTrue(updateResult, "Ошибка при обновлении записи.");


        boolean deleteResult = dataProviderPSQL.deleteDebtorList(debtor.getId());
        assertTrue(deleteResult, "Ошибка при удалении записи.");
    }

    @Test
    void testCRUDMethodsWithIncomingEmails() throws SQLException {
        IncomingEmails email = new IncomingEmails("leraDubina@example.com", "jane.smith@example.com", "john.doe@example.com");
        boolean createResult = dataProviderPSQL.createIncomingEmail(email);
        assertTrue(createResult, "Ошибка при создании записи.");

        IncomingEmails retrievedEmail = dataProviderPSQL.getIncomingEmailById(email.getId());
        assertEquals("leraDubina@example.com", retrievedEmail.getEmailAddress(), "Неверное значение emailAddress.");
        assertEquals("jane.smith@example.com", retrievedEmail.getEmailSender(), "Неверное значение emailSender.");
        assertEquals("john.doe@example.com", retrievedEmail.getEmailReceiver(), "Неверное значение emailReceiver.");

        retrievedEmail.setEmailAddress("leraDubina@sfedu.ru");
        retrievedEmail.setEmailSender("updated.sender@example.com");
        retrievedEmail.setEmailReceiver("updated.receiver@example.com");
        boolean updateResult = dataProviderPSQL.updateIncomingEmail(email.getId(), retrievedEmail);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult = dataProviderPSQL.deleteIncomingEmail(email.getId());
        assertTrue(deleteResult, "Ошибка при удалении записи.");

    }

    @Test
    void testCRUDMethodsWithSeleniumParser() throws SQLException {
        SeleniumParser seleniumParser = new SeleniumParser(1, "new/src/dst/file");
        boolean createResult = dataProviderPSQL.createSeleniumParser(seleniumParser);
        assertTrue(createResult, "Ошибка при создании записи.");

        SeleniumParser retrievedParser = dataProviderPSQL.getSeleniumParserById(seleniumParser.getId());
        assertEquals(1, retrievedParser.getDriver(), "Неверное значение driver.");
        assertEquals("new/src/dst/file", retrievedParser.getSrcDstFiles(), "Неверное значение srcDstFiles.");

        retrievedParser.setDriver(2);
        retrievedParser.setSrcDstFiles("new/src/dst/filee");
        boolean updateResult = dataProviderPSQL.updateSeleniumParser(seleniumParser.getId(), retrievedParser);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult = dataProviderPSQL.deleteSeleniumParser(seleniumParser.getId());
        assertTrue(deleteResult, "Ошибка при удалении записи.");

    }

    @Test
    void testCRUDMethodsWithWindowApp() throws SQLException {
        WindowApp windowApp = new WindowApp("12345", "examplePost@sfedu.ru", "67890", 123456789L, 70, 10, 1001, new Date(2020, 12, 30));
        boolean createResult = dataProviderPSQL.createWindowApp(windowApp);
        assertTrue(createResult, "Ошибка при создании записи.");


        WindowApp retrievedWindowApp = dataProviderPSQL.getWindowAppById(windowApp.getId());
        assertNotNull(retrievedWindowApp, "Запись не найдена.");
        assertEquals("12345", retrievedWindowApp.getKadastrNumber(), "Неверное значение kadastrNumber.");
        assertEquals("examplePost@sfedu.ru", retrievedWindowApp.getPersonalAddress(), "Неверное значение personalAddress.");
        assertEquals("67890", retrievedWindowApp.getPersonalAccount(), "Неверное значение personalAccount.");
        assertEquals(123456789L, retrievedWindowApp.getPersonalNumber(), "Неверное значение personalNumber.");
        assertEquals(70, retrievedWindowApp.getSquare(), "Неверное значение square.");
        assertEquals(10, retrievedWindowApp.getEmailCounter(), "Неверное значение emailCounter.");
        assertEquals(1001, retrievedWindowApp.getPersonalID(), "Неверное значение personalID.");

        retrievedWindowApp.setKadastrNumber("543211255");
        retrievedWindowApp.setPersonalAddress("examplePost@sfedu.ru");
        retrievedWindowApp.setSquare(60);
        boolean updateResult = dataProviderPSQL.updateWindowApp(windowApp.getId(), retrievedWindowApp);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult = dataProviderPSQL.deleteWindowApp(windowApp.getId());
        assertTrue(deleteResult, "Ошибка при удалении записи.");

    }

    @Test
    void testCRUDMethodsWithKadastrList() throws SQLException {
        WithKadastrList withKadastrList = new WithKadastrList("SourceAddress", "DestinationAddress", new Date(2012, 12, 10));
        boolean createResult = dataProviderPSQL.createWithKadastrList(withKadastrList);
        assertTrue(createResult, "Ошибка при создании записи.");

        WithKadastrList retrievedWithKadastrList = dataProviderPSQL.getWithKadastrListById(withKadastrList.getId());
        assertNotNull(retrievedWithKadastrList, "Запись не найдена.");
        assertEquals("SourceAddress", retrievedWithKadastrList.getSource(), "Неверное значение source.");
        assertEquals("DestinationAddress", retrievedWithKadastrList.getDestination(), "Неверное значение destination.");
        assertNotNull(retrievedWithKadastrList.getCreatedTime(), "Неверное значение createdTime.");

        retrievedWithKadastrList.setSource("UpdatedSource");
        retrievedWithKadastrList.setDestination("UpdatedDestination");
        boolean updateResult = dataProviderPSQL.updateWithKadastrList(withKadastrList.getId(), retrievedWithKadastrList);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult = dataProviderPSQL.deleteWithKadastrList(withKadastrList.getId());
        assertTrue(deleteResult, "Ошибка при удалении записи.");
    }
}