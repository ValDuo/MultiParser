package ru.sfedu.dubina.models;

import lombok.*;

import java.io.File;
import java.util.UUID;

@Getter
@Setter
public class CSVReader {
    private String pathName;
    private File csvFile;
    private Integer words;
    private UUID id;

    public CSVReader(String pathName, File csvFile, int words) {
        this.pathName = pathName;
        this.csvFile = csvFile;
        this.words = words;
    }
}
