package ru.sfedu.dubina.models;

import lombok.*;
import ru.sfedu.dubina.utils.Status;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class HistoryContent {
    private UUID id;
    private String className;
    private LocalDateTime createdDate;
    private String actor;
    private String methodName;
    private Map<String, Object> object;
    private Status status;

    public HistoryContent(String className, LocalDateTime createdDate, String actor, String methodName, Map<String, Object> object, Status status) {
        this.id = UUID.randomUUID();
        this.className = className;
        this.createdDate = createdDate;
        this.actor = actor;
        this.methodName = methodName;
        this.object = object;
        this.status = status;
    }


//    public String getClassName() {
//        return className;
//    }
//
//    public void setClassName(String className) {
//        this.className = className;
//    }
//
//    public LocalDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getActor() {
//        return actor;
//    }
//
//    public void setActor(String actor) {
//        this.actor = actor;
//    }
//
//    public String getMethodName() {
//        return methodName;
//    }
//
//    public void setMethodName(String methodName) {
//        this.methodName = methodName;
//    }
//
//    public String getObject() {
//        return object.toString();
//    }
//
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }
//
//    @Override
//    public String toString() {
//        return "HistoryContent{" +
//                "id=" + id +
//                ", className='" + className + '\'' +
//                ", createdDate='" + createdDate + '\'' +
//                ", actor='" + actor + '\'' +
//                ", methodName='" + methodName + '\'' +
//                ", object=" + object +
//                ", status=" + status +
//                '}';
//    }
}
