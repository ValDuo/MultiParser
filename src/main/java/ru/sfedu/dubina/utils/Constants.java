package ru.sfedu.dubina.utils;

import java.text.SimpleDateFormat;

public class Constants {
    public static final String PATH_TO_XML = "mavenDubinaLab2/src/main/java/ru.sfedu.dubina/config.xml";
    public static final String PATH_TO_YAML = "mavenDubinaLab2/src/main/java/ru.sfedu.dubina/config.yaml";
    public static final String PATH_TO_PROP = "mavenDubinaLab2/src/main/java/ru.sfedu.dubina/config.properties";
    public static final String DATABASE_NAME = "historyTest";
    public static final String COLLECTION_NAME = "historyContent";
    public static final String CLIENT_ID = "mongodb://localhost:27017";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String CUTTER_PATH = "C:\\Users\\dvdle\\OneDrive\\Рабочий стол\\mavenproject\\src\\main\\resources\\CSV\\bigcsvcutter.csv";
    public static final String READER_PATH = "C:\\Users\\dvdle\\OneDrive\\Рабочий стол\\mavenproject\\src\\main\\resources\\CSV\\csvreader.csv";
    public static final String EMAILS_PATH = "C:\\Users\\dvdle\\OneDrive\\Рабочий стол\\mavenproject\\src\\main\\resources\\CSV\\emails.csv";
    public static final String PARCER_PATH = "C:\\Users\\dvdle\\OneDrive\\Рабочий стол\\mavenproject\\src\\main\\resources\\CSV\\parser.csv";
    public static final String KADASTRS_PATH = "C:\\Users\\dvdle\\OneDrive\\Рабочий стол\\mavenproject\\src\\main\\resources\\CSV\\withkadastrs.csv";


}
