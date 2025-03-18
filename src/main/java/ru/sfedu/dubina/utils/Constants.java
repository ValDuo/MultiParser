package ru.sfedu.dubina.utils;

import java.text.SimpleDateFormat;

public class Constants {
    public static final String PATH_TO_XML = "mavenproject/src/main/java/ru.sfedu.dubina/config.xml";
    public static final String PATH_TO_YAML = "mavenproject/src/main/java/ru.sfedu.dubina/config.yaml";
    public static final String PATH_TO_PROP = "mavenproject/src/main/java/ru.sfedu.dubina/config.properties";
    public static final String DATABASE_NAME = "historyTest";
    public static final String COLLECTION_NAME = "historyContent";
    public static final String CLIENT_ID = "mongodb://localhost:27017";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String CUTTER_PATH = "mavenproject\\src\\main\\resources\\CSV\\bigcsvcutter.csv";
    public static final String CUTTER_XML_PATH = "mavenproject\\src\\main\\resources\\XML\\bigcsvcutter.xml";
    public static final String READER_PATH = "mavenproject\\src\\main\\resources\\CSV\\csvreader.csv";
    public static final String READER_XML_PATH = "mavenproject\\src\\main\\resources\\XML\\csvreader.xml";
    public static final String EMAILS_PATH = "mavenproject\\src\\main\\resources\\CSV\\emails.csv";
    public static final String EMAILS_XML_PATH = "mavenproject\\src\\main\\resources\\XML\\emails.xml";
    public static final String PARCER_PATH = "mavenproject\\src\\main\\resources\\CSV\\parser.csv";
    public static final String PARCER_XML_PATH = "mavenproject\\src\\main\\resources\\XML\\parser.xml";
    public static final String KADASTRS_PATH = "mavenproject\\src\\main\\resources\\CSV\\withkadastrs.csv";
    public static final String KADASTR_XML_PATH = "mavenproject\\src\\main\\resources\\XML\\withkadastrs.xml";

    private static final String GET_BIG_CSV_CUTTER_BY_ID = "SELECT * FROM bigCSVCutter WHERE id = ?";
    private static final String UPDATE_BIG_CSV_CUTTER =
            "UPDATE bigCSVCutter SET countLine = ?, countFile = ?, date = ?, folder = ?, created = ? WHERE id = ?";
    private static final String DELETE_BIG_CSV_CUTTER = "DELETE FROM bigCSVCutter WHERE id = ?";

    public static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
    public static final String SELECT_BY_ID = "SELECT * FROM %s WHERE id = ?";
    public static final String UPDATE_BY_ID = "UPDATE %s SET %s WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM %s WHERE id = ?";
}
