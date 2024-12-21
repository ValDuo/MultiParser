package org.example.models;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.HashMap;

public class HistoryContent {

    private ObjectId id;
    private String className;
    private String createdDate;
    private String actor;
    private String methodName;
    private Serializable object;
    private Status status;

    public HistoryContent(String className, String createdDate, String actor, String methodName, Serializable object, Status status) {
        this.id = new ObjectId();
        this.className = className;
        this.createdDate = createdDate != null ? createdDate : "Not Available";
        this.actor = actor != null ? actor : "system";
        this.methodName = methodName;
        this.object = object != null ? object : new HashMap<>();
        this.status = status != null ? status : Status.SENT;
    }

    public enum Status {
        SENT, FAILURE, LOST
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
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

    public String getObject() {
        return object.toString();
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
                ", createdDate='" + createdDate + '\'' +
                ", actor='" + actor + '\'' +
                ", methodName='" + methodName + '\'' +
                ", object=" + object +
                ", status=" + status +
                '}';
    }
}
