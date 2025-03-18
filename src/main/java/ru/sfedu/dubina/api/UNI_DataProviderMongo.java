package ru.sfedu.dubina.api;

import com.mongodb.client.*;
import org.apache.log4j.Logger;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import ru.sfedu.dubina.utils.Constants;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

class GenericMongoDAO<T> {
    private final MongoCollection<Document> collection;
    private final Class<T> type;
    private final Logger logger = Logger.getLogger(GenericMongoDAO.class);

    public GenericMongoDAO(Class<T> type, String collectionName) {
        MongoClient mongoClient = MongoClients.create(Constants.CLIENT_ID);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(Constants.DATABASE_NAME);
        this.collection = mongoDatabase.getCollection(collectionName);
        this.type = type;
    }

    public void save(T entity) {
        try {
            Document document = objectToDocument(entity);
            collection.insertOne(document);
            logger.info(type.getSimpleName() + " сохранен: " + document.getString("id"));
        } catch (Exception e) {
            logger.error("Ошибка при сохранении " + type.getSimpleName() + ": " + e.getMessage());
        }
    }
    public T getById(UUID id) {
        try {
            Document document = collection.find(Filters.eq("id", id.toString())).first();
            if (document != null) {
                return documentToObject(document);
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении " + type.getSimpleName() + ": " + e.getMessage());
        }
        throw new NoSuchElementException(type.getSimpleName() + " с ID " + id + " не найден!");
    }
    public void update(T entity) {
        try {
            Document updatedDoc = objectToDocument(entity);
            collection.updateOne(Filters.eq("id", updatedDoc.getString("id")), new Document("$set", updatedDoc));
            logger.info(type.getSimpleName() + " обновлен: " + updatedDoc.getString("id"));
        } catch (Exception e) {
            logger.error("Ошибка при обновлении " + type.getSimpleName() + ": " + e.getMessage());
        }
    }

    public void delete(UUID id) {
        try {
            collection.deleteOne(Filters.eq("id", id.toString()));
            logger.info(type.getSimpleName() + " удален: " + id);
        } catch (Exception e) {
            logger.error("Ошибка при удалении " + type.getSimpleName() + ": " + e.getMessage());
        }
    }

    public List<T> getAll() {
        try {
            return collection.find().map(document -> {
                try {
                    return documentToObject(document);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).into(new ArrayList<>());
        } catch (Exception e) {
            logger.error("Ошибка при получении списка " + type.getSimpleName() + ": " + e.getMessage());
        }
        return Collections.emptyList();
    }


    private Document objectToDocument(T entity) throws IllegalAccessException {
        Document document = new Document();
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(entity);
            document.append(field.getName(), value instanceof UUID ? value.toString() : value);
        }
        return document;
    }

    private T documentToObject(Document document) throws Exception {
        T obj = type.getDeclaredConstructor().newInstance();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = document.get(field.getName());
            if (value != null) {
                if (field.getType().equals(UUID.class)) {
                    field.set(obj, UUID.fromString(value.toString()));
                } else {
                    field.set(obj, value);
                }
            }
        }
        return obj;
    }
}
