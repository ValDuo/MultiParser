package org.example.api;


import java.io.*;
import java.sql.*;

import org.example.models.*;
import org.junit.jupiter.api.*;

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
        BigCSVCutter bigCSVCutter = new BigCSVCutter(50,2, "2002/11/10", "mavenproject/src/test/testFolder", true);

        dataProviderPSQL.createBigCSVCutter(bigCSVCutter);
        BigCSVCutter retrievedUser = dataProviderPSQL.getBigCSVCutterById(bigCSVCutter.getId());
        assertNotNull(retrievedUser);
        assertEquals(50, retrievedUser.getCountLine());
        assertEquals(2, retrievedUser.getCountFile());
        assertEquals("2002/11/10", retrievedUser.getDate());
        assertEquals("mavenproject/src/test/testFolder", retrievedUser.getFolder());
        assertEquals(true, retrievedUser.getCreated());

        retrievedUser.setFolder("mavenproject/src/test/testFolder2");
        retrievedUser.setDate("2020/10/01");
        dataProviderPSQL.updateBigCSVCutter(retrievedUser);


        BigCSVCutter updatedFile = dataProviderPSQL.getBigCSVCutterById(retrievedUser.getId());
        assertEquals("c:/users/lera/somefolder2", updatedFile.getFolder());
        assertEquals(new Date(2012, 13, 30), updatedFile.getDate());

        dataProviderPSQL.deleteBigCSVCutter(bigCSVCutter.getId());
    }

    @Test
    void testCRUDMethodsWithCSVReader() throws SQLException {
        CSVReader csvReader = new CSVReader("mavenproject/src/test/testFolder", new File ("mavenproject\\src\\test\\testFolder\\test.csv"), 15);

        dataProviderPSQL.createCSVReader(csvReader);
        CSVReader retrievedUser = dataProviderPSQL.getCSVReaderById(Integer.parseInt(csvReader.getId()));
        assertNotNull(retrievedUser);
        assertEquals("mavenproject\\src\\test\\testFolder\\test.csv", retrievedUser.getCsvFile());
        assertEquals("mavenproject/src/test/testFolder", retrievedUser.getPathName());
        assertEquals(14, retrievedUser.getWords());

        retrievedUser.setWords(100);
        dataProviderPSQL.updateCSVReader(Integer.parseInt(retrievedUser.getId()), retrievedUser);

        CSVReader updatedUser = dataProviderPSQL.getCSVReaderById(Integer.parseInt(retrievedUser.getId()));
        assertEquals(100, updatedUser.getWords());

        dataProviderPSQL.deleteCSVReader(Integer.parseInt(csvReader.getId()));
    }

    @Test
    void testCRUDMethodsWithDebtorList() throws SQLException {

        DebtorList debtor = new DebtorList("John Doe", "Jane Smith", 5000L, 3000L, new Date(2000,12, 12), new Date(2000, 12, 13), true);
        boolean createResult = dataProviderPSQL.createDebtorList(debtor);
        assertTrue(createResult, "Ошибка при создании записи.");

        DebtorList retrievedDebtor = dataProviderPSQL.getDebtorListById(Integer.parseInt(debtor.getId()));
        assertNotNull(retrievedDebtor, "Запись не найдена.");
        assertEquals("John Doe", retrievedDebtor.getOwnerName(), "Неверное значение ownerName.");
        assertEquals("Jane Smith", retrievedDebtor.getPayerName(), "Неверное значение payerName.");
        assertEquals(5000L, retrievedDebtor.getAccruedMoney(), "Неверное значение accruedMoney.");
        assertEquals(3000L, retrievedDebtor.getReturnedMoney(), "Неверное значение returnedMoney.");
        assertNotNull(retrievedDebtor.getCreateDateOfPayment(), "Неверное значение createDateOfPayment.");
        assertNotNull(retrievedDebtor.getUploadDateOfPayment(), "Неверное значение uploadDateOfPayment.");
        assertTrue(retrievedDebtor.getKadastr(), "Неверное значение kadastr.");

        retrievedDebtor.setAccruedMoney(6000L);
        retrievedDebtor.setReturnedMoney(4000L);
        retrievedDebtor.setKadastr(false);
        boolean updateResult = dataProviderPSQL.updateDebtorList(Integer.parseInt(debtor.getId()), retrievedDebtor);
        assertTrue(updateResult, "Ошибка при обновлении записи.");


        boolean deleteResult = dataProviderPSQL.deleteDebtorList(Integer.parseInt(debtor.getId()));
        assertTrue(deleteResult, "Ошибка при удалении записи.");
    }

    @Test
    void testCRUDMethodsWithIncomingEmails() throws SQLException {
        IncomingEmails email = new IncomingEmails("john.doe@example.com", "jane.smith@example.com", "john.doe@example.com");
        boolean createResult = dataProviderPSQL.createIncomingEmail(email);
        assertTrue(createResult, "Ошибка при создании записи.");

        IncomingEmails retrievedEmail = dataProviderPSQL.getIncomingEmailById(Integer.parseInt(email.getId()));
        assertNotNull(retrievedEmail, "Запись не найдена.");
        assertEquals("john.doe@example.com", retrievedEmail.getEmailAddress(), "Неверное значение emailAddress.");
        assertEquals("jane.smith@example.com", retrievedEmail.getEmailSender(), "Неверное значение emailSender.");
        assertEquals("john.doe@example.com", retrievedEmail.getEmailReceiver(), "Неверное значение emailReceiver.");

        retrievedEmail.setEmailSender("updated.sender@example.com");
        retrievedEmail.setEmailReceiver("updated.receiver@example.com");
        boolean updateResult = dataProviderPSQL.updateIncomingEmail(Integer.parseInt(email.getId()), retrievedEmail);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult = dataProviderPSQL.deleteIncomingEmail(Integer.parseInt(email.getId()));
        assertTrue(deleteResult, "Ошибка при удалении записи.");


    }

    @Test
    void testCRUDMethodsWithSeleniumParser() throws SQLException {
        SeleniumParser seleniumParser = new SeleniumParser(1, "mavenproject\\src\\test\\testFolder");
        boolean createResult = dataProviderPSQL.createSeleniumParser(seleniumParser);

        SeleniumParser retrievedParser = dataProviderPSQL.getSeleniumParserById(Integer.parseInt(seleniumParser.getId()));
        assertEquals(2, retrievedParser.getDriver(), "Неверное значение driver.");
        assertEquals("new/src/dst/file", retrievedParser.getSrcDstFiles(), "Неверное значение srcDstFiles.");

        retrievedParser.setDriver(2);
        retrievedParser.setSrcDstFiles("new/src/dst/file");
        boolean updateResult = dataProviderPSQL.updateSeleniumParser(Integer.parseInt(seleniumParser.getId()), retrievedParser);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult = dataProviderPSQL.deleteSeleniumParser(Integer.parseInt(seleniumParser.getId()));
        assertTrue(deleteResult, "Ошибка при удалении записи.");

    }

    @Test
    void testCRUDMethodsWithWindowApp() throws SQLException {
        WindowApp windowApp = new WindowApp("12345", "Some Address", "67890", 123456789L, 50, 10, 1001, new Date(2020, 12, 30));
        boolean createResult = dataProviderPSQL.createWindowApp(windowApp);
        assertTrue(createResult, "Ошибка при создании записи.");


        WindowApp retrievedWindowApp = dataProviderPSQL.getWindowAppById(Integer.parseInt(windowApp.getId()));
        assertNotNull(retrievedWindowApp, "Запись не найдена.");
        assertEquals("12345", retrievedWindowApp.getKadastrNumber(), "Неверное значение kadastrNumber.");
        assertEquals("Some Address", retrievedWindowApp.getPersonalAddress(), "Неверное значение personalAddress.");
        assertEquals("67890", retrievedWindowApp.getPersonalAccount(), "Неверное значение personalAccount.");
        assertEquals(123456789L, retrievedWindowApp.getPersonalNumber(), "Неверное значение personalNumber.");
        assertEquals(50, retrievedWindowApp.getSquare(), "Неверное значение square.");
        assertEquals(10, retrievedWindowApp.getEmailCounter(), "Неверное значение emailCounter.");
        assertEquals(1001, retrievedWindowApp.getPersonalID(), "Неверное значение personalID.");

        retrievedWindowApp.setKadastrNumber("543211234");
        retrievedWindowApp.setPersonalAddress("Updated Address");
        retrievedWindowApp.setSquare(60);
        boolean updateResult = dataProviderPSQL.updateWindowApp(Integer.parseInt(windowApp.getId()), retrievedWindowApp);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

    }

    @Test
    void testCRUDMethodsWithKadastrList() throws SQLException {
        WithKadastrList withKadastrList = new WithKadastrList("SourceAddress", "DestinationAddress", new Date(2012, 12, 10));
        boolean createResult = dataProviderPSQL.createWithKadastrList(withKadastrList);
        assertTrue(createResult, "Ошибка при создании записи.");

        WithKadastrList retrievedWithKadastrList = dataProviderPSQL.getWithKadastrListById(Integer.parseInt(withKadastrList.getId()));
        assertNotNull(retrievedWithKadastrList, "Запись не найдена.");
        assertEquals("SourceAddress", retrievedWithKadastrList.getSource(), "Неверное значение source.");
        assertEquals("DestinationAddress", retrievedWithKadastrList.getDestination(), "Неверное значение destination.");
        assertNotNull(retrievedWithKadastrList.getCreatedTime(), "Неверное значение createdTime.");

        retrievedWithKadastrList.setSource("UpdatedSource");
        retrievedWithKadastrList.setDestination("UpdatedDestination");
        boolean updateResult = dataProviderPSQL.updateWithKadastrList(Integer.parseInt(withKadastrList.getId()), retrievedWithKadastrList);
        assertTrue(updateResult, "Ошибка при обновлении записи.");

        boolean deleteResult = dataProviderPSQL.deleteWithKadastrList(Integer.parseInt(withKadastrList.getId()));
        assertTrue(deleteResult, "Ошибка при удалении записи.");
    }
}