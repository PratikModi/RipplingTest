package com.rippling.test.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Answer {
    private String id;
    @NonNull
    private String questionId;
    @NonNull
    private User createdBy;
    private LocalDateTime creationTime;
    @NonNull
    private String content;

    public Answer(String questionId, User createdBy, String content) {
        this.id = UUID.randomUUID().toString();
        this.questionId = questionId;
        this.createdBy = createdBy;
        this.content = content;
        this.creationTime = LocalDateTime.now();
    }

}
