package org.example.models;

import lombok.*;

import java.io.File;
import java.util.Date;

@Getter
@Setter
public class BigCSVCutter {
    private String id;
    private Integer countLine;
    private Integer countFile;
    private Date date;
    private File folder;
    private Boolean created;

    public BigCSVCutter(Integer countLine, Integer countFile, Date date, File folder, boolean created) {
        this.id = "123";
        this.countLine = countLine;
        this.countFile = countFile;
        this.date = date;
        this.folder = folder;
        this.created = created;
    }


}
