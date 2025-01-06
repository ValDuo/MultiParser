package ru.sfedu.dubina.models;

import lombok.*;

@Getter
@Setter
public class IncomingEmails {
    private String emailAddress;
    private String emailSender;
    private String emailReceiver;
    private Integer id;

    public IncomingEmails(String emailAddress, String emailSender, String emailReceiver) {
        this.id=111;
        this.emailAddress = emailAddress;
        this.emailSender = emailSender;
        this.emailReceiver = emailReceiver;
    }
}
