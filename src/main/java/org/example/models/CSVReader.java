package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.lang.reflect.Array;

@Getter
@Setter
public class CSVReader {
    private String pathName;
    private File csvFile;
    private Array words;

    public CSVReader(String pathName, File csvFile, Array words) {
        this.pathName = pathName;
        this.csvFile = csvFile;
        this.words = words;
    }
}
