package org.example;

import org.apache.log4j.Logger;
import org.example.api.DataProviderMongo;
import org.example.models.HistoryContent;

import java.util.HashMap;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        HashMap<String, Object> objectState = new HashMap<>();
        objectState.put("field1", "value1");
        objectState.put("field2", 100);

        HistoryContent historyContent = new HistoryContent(
                "LeraСlass",
                "2024-12-09",
                "Lera Dubina",
                "Lera Dubina's method",
                objectState,
                HistoryContent.Status.SENT
        );

        DataProviderMongo dataProvider = new DataProviderMongo();
        try {
            dataProvider.saveHistoryContent(historyContent);
            logger.info("HistoryContent сохранен: " + historyContent);


            HistoryContent retrieved = dataProvider.getHistoryContent("Lera Dubina");
            if (retrieved != null) {
                logger.info("HistoryContent обновлен: " + retrieved);
            } else {
                logger.warn("Не нашлось HistoryContent для заданного автора: Lera Dubina");
            }

            HistoryContent updatedHistory = new HistoryContent(
                    "UpdatedClass",
                    "2024-12-10",
                    "Lera Dubina",
                    "Updated Method",
                    objectState,
                    HistoryContent.Status.FAILURE
            );
            dataProvider.updateHistoryContent("Lera Dubina", updatedHistory);
            logger.info("HistoryContent обновлен: " + updatedHistory);

            dataProvider.deleteHistoryContent("Lera Dubina");
            logger.info("HistoryContent deleted for actor: Lera Dubina");
        } catch (Exception e) {
            logger.error("Произошла ошибка во время исполнения MongoDB операций", e);
        } finally {
            dataProvider.close();
        }
    }
}
