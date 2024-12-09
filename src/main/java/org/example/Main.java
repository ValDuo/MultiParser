package org.example;

import org.apache.log4j.Logger;
import org.example.api.DataProviderMongo;
import org.example.models.HistoryContent;

import java.util.HashMap;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        // Создание объекта HistoryContent
        HashMap<String, Object> objectState = new HashMap<>();
        objectState.put("field1", "value1");
        objectState.put("field2", 100);

        HistoryContent historyContent = new HistoryContent(
                "LeraСlass",
                "2024-12-09",
                "Lera Dubina",
                "Lera Dubina's method",
                objectState.toString(),
                HistoryContent.Status.SUCCESS
        );

        // Работа с MongoDB
        DataProviderMongo dataProvider = new DataProviderMongo();

        try {
            // Сохранение объекта
            dataProvider.saveHistoryContent(historyContent);
            logger.info("HistoryContent saved: " + historyContent);

            // Получение объекта
            HistoryContent retrieved = dataProvider.getHistoryContent("Lera Dubina");
            if (retrieved != null) {
                logger.info("HistoryContent retrieved: " + retrieved);
            } else {
                logger.warn("No HistoryContent found for actor: Lera Dubina");
            }

            // Обновление объекта
            HistoryContent updatedHistory = new HistoryContent(
                    "UpdatedClass",
                    "2024-12-10",
                    "Lera Dubina",
                    "Updated Method",
                    objectState.toString(),
                    HistoryContent.Status.FAILURE
            );
            dataProvider.updateHistoryContent("Lera Dubina", updatedHistory);
            logger.info("HistoryContent updated: " + updatedHistory);

            // Удаление объекта
            dataProvider.deleteHistoryContent("Lera Dubina");
            logger.info("HistoryContent deleted for actor: Lera Dubina");
        } catch (Exception e) {
            logger.error("Error occurred during MongoDB operations", e);
        } finally {
            // Закрытие соединения
            dataProvider.close();
        }
    }
}
