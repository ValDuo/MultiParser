package ru.sfedu.dubina.models;

import lombok.*;

import java.io.File;
import java.util.UUID;

@Getter
@Setter
public class CSVReader {
    private String pathName;
    private String csvFile;
    private Integer words;
    private UUID id;

    public CSVReader(String pathName, String csvFile, int words) {
        this.id = UUID.randomUUID();
        this.pathName = pathName;
        this.csvFile = csvFile;
        this.words = words;
    }
}
