package org.example.models;

import lombok.*;

@Getter
@Setter
public class IncomingEmails {
    private String emailAddress;
    private String emailSender;
    private String emailReceiver;

    public IncomingEmails(String emailAddress, String emailSender, String emailReceiver) {
        this.emailAddress = emailAddress;
        this.emailSender = emailSender;
        this.emailReceiver = emailReceiver;
    }
}
