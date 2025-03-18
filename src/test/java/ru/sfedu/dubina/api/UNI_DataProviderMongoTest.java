package ru.sfedu.dubina.api;

import ru.sfedu.dubina.models.BigCSVCutter;
import ru.sfedu.dubina.models.IncomingEmails;

import java.util.NoSuchElementException;

public class UNI_DataProviderMongoTest {
    public static void main(String[] args) {
        GenericMongoDAO<IncomingEmails> emailDAO = new GenericMongoDAO<>(IncomingEmails.class, "IncomingEmails");

        IncomingEmails email = new IncomingEmails("user@example.com", "sender@example.com", "receiver@example.com");
        emailDAO.save(email);

        try {
            IncomingEmails fetchedEmail = emailDAO.getById(email.getId());
            System.out.println("Найден IncomingEmail от: " + fetchedEmail.getEmailSender());
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        email.setEmailReceiver("newreceiver@example.com");
        emailDAO.update(email);

        emailDAO.delete(email.getId());

        // для bigcsvcutter
        GenericMongoDAO<BigCSVCutter> cutterDAO = new GenericMongoDAO<>(BigCSVCutter.class, "BigCSVCutter");

        BigCSVCutter cutter = new BigCSVCutter(100, 5, "2024-03-18", "/data/csv", true);
        cutterDAO.save(cutter);

        BigCSVCutter fetchedCutter = cutterDAO.getById(cutter.getId());
        System.out.println("CSV Cutter найден, папка: " + fetchedCutter.getFolder());

        fetchedCutter.setFolder("/data/new_csv");
        cutterDAO.update(fetchedCutter);

        cutterDAO.delete(fetchedCutter.getId());

    }
}
