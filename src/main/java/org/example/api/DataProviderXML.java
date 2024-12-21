package org.example.api;

import org.example.models.HistoryContent;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.*;

public class DataProviderXML implements IDataProvider {

    private static final String XML_FILE = "historyContent.xml";

    @Override
    public void initDataSource() throws Exception {
        File file = new File(XML_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public void saveRecord(HistoryContent historyContent) throws Exception {
        Serializer serializer = new Persister();
        File result = new File(XML_FILE);
        serializer.write(historyContent, result);
    }

    @Override
    public HistoryContent getRecordById(String id) throws Exception {
        Serializer serializer = new Persister();
        File source = new File(XML_FILE);
        HistoryContent historyContent = serializer.read(HistoryContent.class, source);
        if (historyContent.getActor().equals(id)) {
            return historyContent;
        }
        return null;
    }

    @Override
    public void updateRecord(String id, HistoryContent historyContent) throws Exception {

    }

    @Override
    public void deleteRecord(String id) throws Exception {

    }

    @Override
    public void close() {
    }
}
