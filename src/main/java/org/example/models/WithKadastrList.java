package org.example.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class WithKadastrList {
    private String source;
    private String destination;
    private Date createdTime;
    private String id;

    public WithKadastrList(String source, String destination, Date createdTime){
        this.source = source;
        this.destination = destination;
        this.createdTime = createdTime;
        this.id = "1234567";
    }

}
