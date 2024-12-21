package org.example.api;

import org.example.models.HistoryContent;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.util.*;

public class DataProviderCsv implements IDataProvider {
    private String csvFilePath;

    public DataProviderCsv(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public void saveRecord(HistoryContent record) throws Exception {
        try (FileWriter writer = new FileWriter(csvFilePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            // Преобразование объекта в строку CSV
            String recordAsCsv = toCsv(record);
            bufferedWriter.write(recordAsCsv);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new Exception("Error saving record to CSV file", e);
        }
    }

    @Override
    public HistoryContent getRecordById(String id) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader);
            for (CSVRecord record : parser) {
                if (record.get("id").equals(id)) {
                    return fromCsv(record);
                }
            }
            return null; // Record not found
        } catch (IOException e) {
            throw new Exception("Error reading CSV file", e);
        }
    }

    @Override
    public void updateRecord(String id, HistoryContent updatedRecord) throws Exception {
        File inputFile = new File(csvFilePath);
        File tempFile = new File("temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader);
            boolean updated = false;

            // Iterate through records and update the matching one
            for (CSVRecord record : parser) {
                if (record.get("id").equals(id)) {
                    writer.write(toCsv(updatedRecord));
                    writer.newLine();
                    updated = true;
                } else {
                    writer.write(record.toString());
                    writer.newLine();
                }
            }

            if (!updated) {
                throw new Exception("Record with id " + id + " not found.");
            }

            // Replace the original file with the updated one
            if (!inputFile.delete()) {
                throw new Exception("Failed to delete original file.");
            }
            if (!tempFile.renameTo(inputFile)) {
                throw new Exception("Failed to rename temporary file.");
            }

        } catch (IOException e) {
            throw new Exception("Error updating record in CSV file", e);
        }
    }

    @Override
    public void deleteRecord(String id) throws Exception {
        File inputFile = new File(csvFilePath);
        File tempFile = new File("temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader);

            boolean deleted = false;

            // Iterate through records and remove the one with matching ID
            for (CSVRecord record : parser) {
                if (record.get("id").equals(id)) {
                    deleted = true;
                } else {
                    writer.write(record.toString());
                    writer.newLine();
                }
            }

            if (!deleted) {
                throw new Exception("Record with id " + id + " not found.");
            }

            // Replace the original file with the updated one
            if (!inputFile.delete()) {
                throw new Exception("Failed to delete original file.");
            }
            if (!tempFile.renameTo(inputFile)) {
                throw new Exception("Failed to rename temporary file.");
            }

        } catch (IOException e) {
            throw new Exception("Error deleting record from CSV file", e);
        }
    }

    @Override
    public void initDataSource() throws Exception {

    }

    private String toCsv(HistoryContent record) {
        // Преобразуем объект в строку CSV
        return record.getId() + "," + record.getClassName() + "," + record.getCreatedDate() + ","
                + record.getActor() + "," + record.getMethodName() + "," + record.getStatus();
    }

    private HistoryContent fromCsv(CSVRecord record) {
        // Преобразуем строку CSV обратно в объект
        String id = record.get("id");
        String className = record.get("className");
        String createdDate = record.get("createdDate");
        String actor = record.get("actor");
        String methodName = record.get("methodName");
        HistoryContent.Status status = HistoryContent.Status.valueOf(record.get("status"));

        Map<String, Object> object = new HashMap<>(); // Пример, если объект нужен

        return new HistoryContent(className, createdDate, actor, methodName, object, status);
    }

    @Override
    public void close() throws Exception {
        // В данном случае метод close может быть не обязательным, если вы не используете ресурсы, требующие явного закрытия
        // Однако, если вам нужно, можно реализовать закрытие файлов, потоков и т.д.
    }
}
