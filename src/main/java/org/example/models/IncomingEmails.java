package org.example.models;

import lombok.*;

@Getter
@Setter
public class IncomingEmails {
    private String emailAddress;
    private String emailSender;
    private String emailReceiver;
    private String id;

    public IncomingEmails(String emailAddress, String emailSender, String emailReceiver) {
        this.id="123456";
        this.emailAddress = emailAddress;
        this.emailSender = emailSender;
        this.emailReceiver = emailReceiver;
    }
}
