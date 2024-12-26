package org.example.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class WindowApp {
    private String kadastrNumber;
    private String personalAddress;
    private String personalAccount;
    private Long personalNumber;
    private Integer square;
    private Integer emailCounter;
    private Integer personalID;
    private LocalDateTime createDate;
    private Date uploadDate;

    public WindowApp(String kadastrNumber, String personalAddress, String personalAccount, Long personalNumber, Integer square, Integer emailCounter, Integer personalID, Date uploadDate) {
        this.kadastrNumber = kadastrNumber;
        this.personalAddress = personalAddress;
        this.personalAccount = personalAccount;
        this.personalNumber = personalNumber;
        this.square = square;
        this.emailCounter = emailCounter;
        this.personalID = personalID;
        this.createDate = LocalDateTime.now();
        this.uploadDate = uploadDate;
    }
}
