package ru.sfedu.dubina.models;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class WithKadastrList {
    private String source;
    private String destination;
    private Date createdTime;
    private UUID id;

    public WithKadastrList(String source, String destination, Date createdTime){
        this.source = source;
        this.destination = destination;
        this.createdTime = createdTime;
    }

}
