package org.example.api;

import com.mongodb.client.*;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.example.models.HistoryContent;
import ru.sfedu.dubina.Constants;
import com.mongodb.client.model.Filters;

import java.time.LocalDateTime;

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
    public HistoryContent getHistoryContent(String id) {
        try{
            Document document = collection.find(Filters.eq("id", id)).first();
            if (document != null) {
                HistoryContent historyContent = new HistoryContent();
                historyContent.setId(document.getString("id"));
                historyContent.setClassName(document.getString("className"));
                historyContent.setCreatedDate(LocalDateTime.parse(document.getString("createdDate")));
                historyContent.setActor(document.getString("actor"));
                historyContent.setMethodName(document.getString("methodName"));

                Document objectDoc = document.get("object", Document.class);

                if (objectDoc != null){
                    historyContent.setObject(objectDoc);
                }
                historyContent.setStatus(HistoryContent.Status.valueOf(document.getString("status")));
                return historyContent;
            }
        }
        catch (Exception e){
            logger.error("error при получении данных");
        }
        return null;
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

}