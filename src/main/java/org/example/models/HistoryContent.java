package org.example.models;


import org.bson.types.ObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class HistoryContent {

    private ObjectId id;
    private String className;
    private Date createdDate;
    private String actor;
    private String methodName;
    private Map<String, Object> object;
    private Status status;

    public HistoryContent(String className, String createdDate, String actor, String methodName, String object, Status status) {
        this.id = new ObjectId();
        this.createdDate = new Date();
        this.actor = "system";
        this.object = new HashMap<>();
    }

    public HistoryContent(String className, String methodName, Map<String, Object> object, Status status) {
        this.id = new ObjectId();
        this.createdDate = createdDate != null ? new Date(String.valueOf(createdDate)) : new Date();
        this.actor = actor != null ? actor : "system";
        this.className = className;
        this.methodName = methodName;
        this.object = object;
        this.status = status;
    }
    public enum Status {
        SUCCESS, FAILURE
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, Object> getObject() {
        return object;
    }

    public void setObject(Map<String, Object> object) {
        this.object = object;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HistoryContent{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", createdDate=" + createdDate +
                ", actor='" + actor + '\'' +
                ", methodName='" + methodName + '\'' +
                ", object=" + object +
                ", status=" + status +
                '}';
    }
}