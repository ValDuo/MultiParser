package ru.sfedu.dubina.lab2;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Test")
public class TestEntity implements Serializable {
    @Id
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESC", nullable = false)
    private String description;
    @Column(name = "DATE", nullable = false)
    private Date dateCreated;
    @Column(name = "CHECK", nullable = false)
    private Boolean check;
    @Embedded
    private AuditInfo auditInfo;

    public TestEntity() {
    }
    public TestEntity(Long id, String name, String description, Date dateCreated, Boolean check) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.check = check;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getCheck() {
        return check;
    }
    public void setCheck(Boolean check) {
        this.check = check;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }
}


