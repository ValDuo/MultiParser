package org.example.api;

import org.example.models.PersForApi;

public class Main {
    public static void main(String[] args) {
        DataProviderPostgres dataProvider = new DataProviderPostgres();
        dataProvider.initDataSource();


        PersForApi newPerson = new PersForApi(null, "John Doe", "john.doe@example.com");
        dataProvider.saveRecord(newPerson);


        PersForApi person = dataProvider.getRecordById(1L);
        System.out.println(person);


        dataProvider.deleteRecord(1L);


        dataProvider.closeConnection();
    }
}
