package org.example.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class DebtorList {
    private String ownerName;
    private String payerName;
    private Long accruedMoney;
    private Long returnedMoney;
    private LocalDateTime createDateOfPayment;
    private Date uploadDateOfPayment;
    private Boolean kadastr;
    private String id;

    public DebtorList(String ownerName, String payerName, Long accruedMoney, Long returnedMoney, Date createDateOfPayment, Date uploadDateOfPayment, Boolean kadastr) {
        this.id = "12345";
        this.ownerName = ownerName;
        this.payerName = payerName;
        this.accruedMoney = accruedMoney;
        this.returnedMoney = returnedMoney;
        this.createDateOfPayment = LocalDateTime.now();
        this.uploadDateOfPayment = uploadDateOfPayment;
        this.kadastr = kadastr;
    }
}
