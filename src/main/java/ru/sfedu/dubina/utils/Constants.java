package ru.sfedu.dubina.utils;

import java.text.SimpleDateFormat;

public class Constants {
    public static final String PATH_TO_XML = "mavenproject/src/main/java/ru.sfedu.dubina/config.xml";
    public static final String PATH_TO_YAML = "mavenproject/src/main/java/ru.sfedu.dubina/config.yaml";
    public static final String PATH_TO_PROP = "mavenproject/src/main/java/ru.sfedu.dubina/config.properties";
    public static final String DATABASE_NAME = "historyTest";
    public static final String COLLECTION_NAME = "historyContent";
    public static final String CLIENT_ID = "mongodb://localhost:27017";
    public static final String CUTTER_PATH = "mavenproject\\src\\main\\resources\\CSV\\bigcsvcutter.csv";
    public static final String CUTTER_XML_PATH = "mavenproject\\src\\main\\resources\\XML\\bigcsvcutter.xml";
    public static final String READER_PATH = "mavenproject\\src\\main\\resources\\CSV\\csvreader.csv";
    public static final String READER_XML_PATH = "mavenproject\\src\\main\\resources\\XML\\csvreader.xml";
    public static final String EMAILS_PATH = "mavenproject\\src\\main\\resources\\CSV\\emails.csv";
    public static final String PARCER_PATH = "mavenproject\\src\\main\\resources\\CSV\\parser.csv";
    public static final String KADASTRS_PATH = "mavenproject\\src\\main\\resources\\CSV\\withkadastrs.csv";



    public static final String DEFAULT_CONFIG_PATH = "mavenproject\\src\\main\\hibernate.cfg.xml";

    public static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
    public static final String SELECT_BY_ID = "SELECT * FROM %s WHERE id = ?";
    public static final String UPDATE_BY_ID = "UPDATE %s SET %s WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM %s WHERE id = ?";
}
