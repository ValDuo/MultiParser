package ru.sfedu.dubina.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class SeleniumParser {
    private Integer driver;
    private String srcDstFiles;
    private UUID id;

    public SeleniumParser(Integer driver, String srcDstFiles) {
        this.driver = driver;
        this.srcDstFiles = srcDstFiles;
    }
}
