package ru.sfedu.dubina.models;

import lombok.*;

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

    public HistoryContent(String className, LocalDateTime createdDate, String actor, String methodName, Status status) {
        this.id = UUID.randomUUID();
        this.className = className;
        this.createdDate = createdDate;
        this.actor = actor;
        this.methodName = methodName;
        this.status = status;
    }

}
