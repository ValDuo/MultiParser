package org.example.api;

import org.example.models.PersForApi;

public interface IDataProvider <T> {
    void saveRecord(T record);
    void deleteRecord(Long id);
    T getRecordById(Long id);
    void initDataSource();
}
