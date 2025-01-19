package ru.sfedu.dubina.models;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class PersForApi implements Serializable {
    private UUID id;
    private String name;
    private String email;

    public PersForApi(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }




}

