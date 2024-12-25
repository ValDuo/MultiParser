package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeleniumParser {
    private Integer driver;
    private String srcDstFiles;

    public SeleniumParser(Integer driver, String srcDstFiles) {
        this.driver = driver;
        this.srcDstFiles = srcDstFiles;
    }
}
