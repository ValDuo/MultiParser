package org.example.api;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.models.HistoryContent;
import ru.sfedu.dubina.Constants;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class DataProviderMongo {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DataProviderMongo() {
        mongoClient = MongoClients.create(Constants.CLIENT_ID);
        database = mongoClient.getDatabase(Constants.DATABASE_NAME);
        collection = database.getCollection(Constants.COLLECTION_NAME);
    }

    public void saveHistoryContent(HistoryContent historyContent) {
        Document document = new Document("className", historyContent.getClassName())
                .append("createdDate", historyContent.getCreatedDate())
                .append("actor", historyContent.getActor())
                .append("methodName", historyContent.getMethodName())
                .append("object", historyContent.getObject())
                .append("status", historyContent.getStatus().toString());

        collection.insertOne(document);
    }
    public HistoryContent getHistoryContent(String actor) {
        Bson filter = Filters.eq("actor", actor);
        Document document = collection.find(filter).first();

        if (document != null) {
            return new HistoryContent(
                    document.getString("className"),
                    document.getString("createdDate"),
                    document.getString("actor"),
                    document.getString("methodName"),
                    document.getString("object"),
                    HistoryContent.Status.valueOf(document.getString("status"))
            );
        }
        return null;
    }
    public void updateHistoryContent(String actor, HistoryContent historyContent) {
        Bson filter = Filters.eq("actor", actor);
        Bson update = Updates.combine(
                Updates.set("className", historyContent.getClassName()),
                Updates.set("createdDate", historyContent.getCreatedDate()),
                Updates.set("methodName", historyContent.getMethodName()),
                Updates.set("object", historyContent.getObject()),
                Updates.set("status", historyContent.getStatus().toString())
        );
        collection.updateOne(filter, update);
    }
    public void deleteHistoryContent(String actor) {
        Bson filter = Filters.eq("actor", actor);
        collection.deleteOne(filter);
    }

    public void close() {
        mongoClient.close();
    }
}