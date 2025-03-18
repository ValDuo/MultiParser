package ru.sfedu.dubina.api;

import ru.sfedu.dubina.models.BigCSVCutter;
import ru.sfedu.dubina.models.CSVReader;
import ru.sfedu.dubina.models.IncomingEmails;


import java.util.NoSuchElementException;

public class DataProviderMongoTest {

    public void testCRUDWithMongoDB(){
        DataProviderMongo dataProvider = new DataProviderMongo();


        BigCSVCutter cutter = new BigCSVCutter(100, 5, "2024-03-18", "/data/files", true);
        dataProvider.saveBigCSVCutter(cutter);


        try {
            BigCSVCutter fetchedCutter = dataProvider.getBigCSVCutterById(cutter.getId());
            System.out.println("Найден: " + fetchedCutter.getFolder());
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }


        cutter.setFolder("/data/new_folder");
        dataProvider.updateBigCSVCutter(cutter);


        dataProvider.deleteBigCSVCutter(cutter.getId());

        // для csv reader
        CSVReader reader = new CSVReader("/data/files", "example.csv", 500);
        dataProvider.saveCSVReader(reader);

        try {
            CSVReader fetchedReader = dataProvider.getCSVReaderById(reader.getId());
            System.out.println("Найден CSVReader: " + fetchedReader.getCsvFile());
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        reader.setCsvFile("new_file.csv");
        dataProvider.updateCSVReader(reader);

        dataProvider.deleteCSVReader(reader.getId());

        // incoming emails
        IncomingEmails email = new IncomingEmails("user@example.com", "sender@example.com", "receiver@example.com");
        dataProvider.saveIncomingEmail(email);

        // Чтение
        try {
            IncomingEmails fetchedEmail = dataProvider.getIncomingEmailById(email.getId());
            System.out.println("Найден IncomingEmail от: " + fetchedEmail.getEmailSender());
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Обновление
        email.setEmailReceiver("newreceiver@example.com");
        dataProvider.updateIncomingEmail(email);

        // Удаление
        dataProvider.deleteIncomingEmail(email.getId());
    }
}
