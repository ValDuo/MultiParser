package org.example.api;

import org.example.models.HistoryContent;

public interface IDataProvider {
    void saveRecord(HistoryContent historyContent) throws Exception;
    HistoryContent getRecordById(String id) throws Exception;
    void updateRecord(String id, HistoryContent historyContent) throws Exception;
    void deleteRecord(String id) throws Exception;
    void initDataSource() throws Exception;
    void close() throws Exception;
}
