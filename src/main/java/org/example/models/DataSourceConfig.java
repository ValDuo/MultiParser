package org.example.models;

import org.example.api.IDataProvider;
import org.example.api.DataProviderMongo;
import org.example.api.DataProviderPostgres;

public class DataSourceConfig {

    public static IDataProvider getDataProvider(String dbType) {
        switch (dbType.toLowerCase()) {
            case "mongodb":
                return new DataProviderMongo();
            case "postgresql":
                return new DataProviderPostgres();
            default:
                throw new IllegalArgumentException("Unsupported database type");
        }
    }
}
