//package ru.sfedu.dubina.api;
//
//import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;
//import org.apache.log4j.Logger;
//import ru.sfedu.dubina.models.PersForApi;
//import ru.sfedu.dubina.utils.Constants;
//
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class DataProviderCsv implements IDataProvider<PersForApi> {
//    Logger logger = Logger.getLogger(DataProviderCsv.class);
//    private List<PersForApi> persInfList;
//    public DataProviderCsv() {
//        this.persInfList = new ArrayList<>();
//        initDataSource();
//    }
//
//    @Override
//    public void saveRecord(PersForApi record) {
//        try (CSVWriter writer = new CSVWriter(new FileWriter(Constants.csvFilePath, true))) {
//            String[] values = {String.valueOf(record.getId()), record.getName(), record.getEmail()};
//            writer.writeNext(values);
//            persInfList.add(record);
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    public void deleteRecord(Long id) {
//        persInfList.removeIf(person -> person.getId() == id);
//        try (CSVWriter writer = new CSVWriter(new FileWriter(Constants.csvFilePath))) {
//            for (PersForApi person : persInfList) {
//                String[] values = {String.valueOf(person.getId()), person.getName(), person.getEmail()};
//                writer.writeNext(values);
//            }
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    public PersForApi getRecordById(Long id) {
//        for (PersForApi person : persInfList) {
//            if (person.getId().equals(id)) {
//                return person;
//            }
//        }
//        return null;
//    }
//
//        @Override
//        public void initDataSource() {
//            persInfList = new ArrayList<>();
//            File file = new File(Constants.csvFilePath);
//            if(!file.exists()) {
//                try{
//                    file.createNewFile();
//                } catch (IOException e) {
//                    logger.error(e.getMessage());
//                }
//            }else{
//                try(CSVReader reader = new CSVReader(new FileReader(Constants.csvFilePath))) {
//                    String[] nextLine;
//                    while((nextLine = reader.readNext())!=null){
//                        Long id = Long.parseLong(nextLine[0]);
//                        String name = nextLine[1];
//                        String email = nextLine[2];
//                        persInfList.add(new PersForApi(id, name, email));
//                    }
//                }catch (Exception e){
//                    logger.error(e.getMessage());
//                }
//            }
//        }
//
//}
