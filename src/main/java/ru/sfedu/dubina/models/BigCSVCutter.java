package ru.sfedu.dubina.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class BigCSVCutter {
    private UUID id;
    private Integer countLine;
    private Integer countFile;
    private String date;
    private String folder;
    private Boolean created;

    public BigCSVCutter(Integer countLine, Integer countFile, String date, String folder, boolean created) {
        this.countLine = countLine;
        this.countFile = countFile;
        this.date = date;
        this.folder = folder;
        this.created = created;
    }


}
