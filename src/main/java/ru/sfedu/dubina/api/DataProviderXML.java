package ru.sfedu.dubina.api;

import org.apache.log4j.Logger;
import ru.sfedu.dubina.models.PersForApi;
import ru.sfedu.dubina.models.PersInfList;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.dubina.utils.Constants;


import java.io.*;
import java.util.*;

public abstract class DataProviderXML implements IDataProvider<PersForApi> {
    private List<PersForApi> persInfList;
    Logger logger = Logger.getLogger(DataProviderCsv.class);
    @Override
    public void saveRecord(PersForApi record) {
        try {
            persInfList.add(record);
            Serializer serializer = new Persister();
            PersInfList container = new PersInfList(persInfList);
            serializer.write(container, new File(Constants.xmlFilePath));
        } catch (Exception e) {
           logger.error(e.getMessage());
        }
    }
    @Override
    public void deleteRecord(Long id) {
        try {
            persInfList.removeIf(p -> p.getId().equals(id));
            Serializer serializer = new Persister();
            PersInfList container = new PersInfList(persInfList);
            serializer.write(container, new File(Constants.xmlFilePath));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public PersForApi getRecordById(Long id) {
        try {
            return persInfList.stream()
                    .filter(record -> record.getId() == id)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void initDataSource() {
        try {
            File file = new File(Constants.xmlFilePath);
            if (file.exists() && file.length() > 0) {
                Serializer serializer = new Persister();
                PersInfList container = serializer.read(PersInfList.class, file);
                persInfList = container.getRecords();
            } else {
                persInfList = new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            persInfList = new ArrayList<>();
        }
    }
}
