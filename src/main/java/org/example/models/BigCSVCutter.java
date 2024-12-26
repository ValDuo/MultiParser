package org.example.models;

import lombok.*;

import java.io.File;
import java.util.Date;

@Getter
@Setter
public class BigCSVCutter {
    private Integer countLine;
    private Integer countFile;
    private Date date;
    private File folder;
    private boolean created;

    public BigCSVCutter(Integer countLine, Integer countFile, Date date, File folder, boolean created) {
        this.countLine = countLine;
        this.countFile = countFile;
        this.date = date;
        this.folder = folder;
        this.created = created;
    }


}
