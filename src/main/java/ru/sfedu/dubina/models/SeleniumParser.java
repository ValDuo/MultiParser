package ru.sfedu.dubina.models;

import lombok.*;

@Getter
@Setter
public class SeleniumParser {
    private Integer driver;
    private String srcDstFiles;
    private String id;

    public SeleniumParser(Integer driver, String srcDstFiles) {
        this.id = "12";
        this.driver = driver;
        this.srcDstFiles = srcDstFiles;
    }
}
