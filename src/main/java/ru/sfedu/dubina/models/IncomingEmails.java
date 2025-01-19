package ru.sfedu.dubina.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class IncomingEmails {
    private String emailAddress;
    private String emailSender;
    private String emailReceiver;
    private UUID id;

    public IncomingEmails(String emailAddress, String emailSender, String emailReceiver) {
        this.id = UUID.randomUUID();
        this.emailAddress = emailAddress;
        this.emailSender = emailSender;
        this.emailReceiver = emailReceiver;
    }
}
