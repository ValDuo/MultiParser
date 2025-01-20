//package ru.sfedu.dubina.api;
//
//import ru.sfedu.dubina.models.CSVReader;
//import org.junit.jupiter.api.Test;
//
//
//import java.util.Map;
//
//public class DataProviderMongoTest {
//
//    public void testCRUDWithMongoDB(){
//        DataProviderMongo dataProvider = new DataProviderMongo();
//
//        CSVReader content = new HistoryContent();
//        content.setId("1");
//        content.setClassName("ЛераДубина");
//        content.setMethodName("exampleMethod");
//        content.setObject(Map.of("key1", "value1", "key2", "value2"));
//        content.setStatus(HistoryContent.Status.SENT);
//
//        dataProvider.saveHistoryContent(content);
//
//        HistoryContent retrieved = dataProvider.getHistoryContent("1");
//        System.out.println(retrieved);
//
//        content.setStatus(HistoryContent.Status.FAILURE);
//        dataProvider.updateHistoryContent(content);
//
//        dataProvider.deleteHistoryContent("1");
//    }
//}
