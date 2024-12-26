package org.example.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class WithKadastrList {
    private String source;
    private String destination;
    private Date createdTime;

    public WithKadastrList(String source, String destination, Date createdTime){
        this.source = source;
        this.destination = destination;
        this.createdTime = createdTime;
    }

}
