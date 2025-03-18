package ru.sfedu.dubina.api;

import com.mongodb.client.*;
import org.apache.log4j.Logger;
import org.bson.Document;

import ru.sfedu.dubina.models.BigCSVCutter;
import ru.sfedu.dubina.models.CSVReader;
import ru.sfedu.dubina.models.HistoryContent;
import com.mongodb.client.model.Filters;
import ru.sfedu.dubina.models.IncomingEmails;
import ru.sfedu.dubina.utils.Constants;

import java.util.NoSuchElementException;
import java.util.UUID;

public class DataProviderMongo {
    private MongoCollection<Document> collection;
    private Logger logger = Logger.getLogger(DataProviderMongo.class);

    public DataProviderMongo() {
        MongoClient mongoClient = MongoClients.create(Constants.CLIENT_ID);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(Constants.DATABASE_NAME);
        this.collection = mongoDatabase.getCollection(Constants.COLLECTION_NAME);
    }

    public void saveHistoryContent(HistoryContent historyContent) {
        try {
            Document document = new Document("id", historyContent.getId())
                    .append("className", historyContent.getClassName())
                    .append("createdDate", historyContent.getCreatedDate())
                    .append("actor", historyContent.getActor())
                    .append("methodName", historyContent.getMethodName())
                    .append("object", historyContent.getObject())
                    .append("status", historyContent.getStatus().toString());

            collection.insertOne(document);
        }
        catch (Exception exception) {
            logger.error("произошла ошибка при сохранении");
        }
    }

    public void updateHistoryContent(HistoryContent historyContent) {
        try{
            Document updatedDoc = new Document("className", historyContent.getClassName())
                    .append("createdDate", historyContent.getCreatedDate().toString())
                    .append("actor", historyContent.getActor())
                    .append("methodName", historyContent.getMethodName())
                    .append("object", historyContent.getObject())
                    .append("status", historyContent.getStatus().name());
            collection.updateOne(Filters.eq("id", historyContent.getId()), new Document("$set", updatedDoc));
        }
        catch (Exception e) {
            logger.error("ошибка при обновлении");
        }
    }
    public void deleteHistoryContent(String id) {
        try{
            collection.deleteOne(Filters.eq("id", id));
        }
        catch (Exception e){
            logger.error("ошибка при удалении");
        }

    }

    //для bigcsv
    public void saveBigCSVCutter(BigCSVCutter cutter) {
        try {
            Document document = new Document("id", cutter.getId().toString())
                    .append("countLine", cutter.getCountLine())
                    .append("countFile", cutter.getCountFile())
                    .append("date", cutter.getDate())
                    .append("folder", cutter.getFolder())
                    .append("created", cutter.getCreated());

            collection.insertOne(document);
            logger.info("BigCSVCutter успешно сохранен: " + cutter.getId());
        } catch (Exception e) {
            logger.error("Ошибка при сохранении BigCSVCutter: " + e.getMessage());
        }
    }

    public BigCSVCutter getBigCSVCutterById(UUID id) {
        try {
            Document document = collection.find(Filters.eq("id", id.toString())).first();
            if (document != null) {
                return new BigCSVCutter(
                        document.getInteger("countLine"),
                        document.getInteger("countFile"),
                        document.getString("date"),
                        document.getString("folder"),
                        document.getBoolean("created")
                );
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении BigCSVCutter: " + e.getMessage());
        }
        throw new NoSuchElementException("BigCSVCutter с ID " + id + " не найден!");
    }

    // Обновление объекта
    public void updateBigCSVCutter(BigCSVCutter cutter) {
        try {
            Document updatedDoc = new Document("countLine", cutter.getCountLine())
                    .append("countFile", cutter.getCountFile())
                    .append("date", cutter.getDate())
                    .append("folder", cutter.getFolder())
                    .append("created", cutter.getCreated());

            collection.updateOne(Filters.eq("id", cutter.getId().toString()), new Document("$set", updatedDoc));
            logger.info("BigCSVCutter обновлен: " + cutter.getId());
        } catch (Exception e) {
            logger.error("Ошибка при обновлении BigCSVCutter: " + e.getMessage());
        }
    }

    public void deleteBigCSVCutter(UUID id) {
        try {
            collection.deleteOne(Filters.eq("id", id.toString()));
            logger.info("BigCSVCutter удален: " + id);
        } catch (Exception e) {
            logger.error("Ошибка при удалении BigCSVCutter: " + e.getMessage());
        }
    }

    //для csvreader

    public void saveCSVReader(CSVReader reader) {
        try {
            Document document = new Document("id", reader.getId().toString())
                    .append("pathName", reader.getPathName())
                    .append("csvFile", reader.getCsvFile())
                    .append("words", reader.getWords());

            collection.insertOne(document);
            logger.info("CSVReader успешно сохранен: " + reader.getId());
        } catch (Exception e) {
            logger.error("Ошибка при сохранении CSVReader: " + e.getMessage());
        }
    }

    public CSVReader getCSVReaderById(UUID id) {
        try {
            Document document = collection.find(Filters.eq("id", id.toString())).first();
            if (document != null) {
                return new CSVReader(
                        document.getString("pathName"),
                        document.getString("csvFile"),
                        document.getInteger("words")
                );
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении CSVReader: " + e.getMessage());
        }
        throw new NoSuchElementException("CSVReader с ID " + id + " не найден!");
    }

    public void updateCSVReader(CSVReader reader) {
        try {
            Document updatedDoc = new Document("pathName", reader.getPathName())
                    .append("csvFile", reader.getCsvFile())
                    .append("words", reader.getWords());

            collection.updateOne(Filters.eq("id", reader.getId().toString()), new Document("$set", updatedDoc));
            logger.info("CSVReader обновлен: " + reader.getId());
        } catch (Exception e) {
            logger.error("Ошибка при обновлении CSVReader: " + e.getMessage());
        }
    }

    public void deleteCSVReader(UUID id) {
        try {
            collection.deleteOne(Filters.eq("id", id.toString()));
            logger.info("CSVReader удален: " + id);
        } catch (Exception e) {
            logger.error("Ошибка при удалении CSVReader: " + e.getMessage());
        }
    }

    //для incoming emails
    public void saveIncomingEmail(IncomingEmails email) {
        try {
            Document document = new Document("id", email.getId().toString())
                    .append("emailAddress", email.getEmailAddress())
                    .append("emailSender", email.getEmailSender())
                    .append("emailReceiver", email.getEmailReceiver());

            collection.insertOne(document);
            logger.info("IncomingEmail успешно сохранен: " + email.getId());
        } catch (Exception e) {
            logger.error("Ошибка при сохранении IncomingEmail: " + e.getMessage());
        }
    }

    public IncomingEmails getIncomingEmailById(UUID id) {
        try {
            Document document = collection.find(Filters.eq("id", id.toString())).first();
            if (document != null) {
                return new IncomingEmails(
                        document.getString("emailAddress"),
                        document.getString("emailSender"),
                        document.getString("emailReceiver")
                );
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении IncomingEmail: " + e.getMessage());
        }
        throw new NoSuchElementException("IncomingEmail с ID " + id + " не найден!");
    }

    public void updateIncomingEmail(IncomingEmails email) {
        try {
            Document updatedDoc = new Document("emailAddress", email.getEmailAddress())
                    .append("emailSender", email.getEmailSender())
                    .append("emailReceiver", email.getEmailReceiver());

            collection.updateOne(Filters.eq("id", email.getId().toString()), new Document("$set", updatedDoc));
            logger.info("IncomingEmail обновлен: " + email.getId());
        } catch (Exception e) {
            logger.error("Ошибка при обновлении IncomingEmail: " + e.getMessage());
        }
    }

    public void deleteIncomingEmail(UUID id) {
        try {
            collection.deleteOne(Filters.eq("id", id.toString()));
            logger.info("IncomingEmail удален: " + id);
        } catch (Exception e) {
            logger.error("Ошибка при удалении IncomingEmail: " + e.getMessage());
        }
    }
}