package org.example.models;

import lombok.*;

@Getter
@Setter
public class BigCSVCutter {
    private String id;
    private Integer countLine;
    private Integer countFile;
    private String date;
    private String folder;
    private Boolean created;

    public BigCSVCutter(Integer countLine, Integer countFile, String date, String folder, boolean created) {
        this.id = "12223";
        this.countLine = countLine;
        this.countFile = countFile;
        this.date = date;
        this.folder = folder;
        this.created = created;
    }


}
