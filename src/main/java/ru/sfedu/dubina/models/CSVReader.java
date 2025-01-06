package ru.sfedu.dubina.models;

import lombok.*;

import java.io.File;

@Getter
@Setter
public class CSVReader {
    private String pathName;
    private File csvFile;
    private Integer words;
    private String id;

    public CSVReader(String pathName, File csvFile, int words) {
        this.id = "1234";
        this.pathName = pathName;
        this.csvFile = csvFile;
        this.words = words;
    }
}
