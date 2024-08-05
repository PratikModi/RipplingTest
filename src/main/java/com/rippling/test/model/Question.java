package com.rippling.test.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Question {
    private String id;
    @NotNull
    @Size(max = 250)
    private String title;
    @NotNull
    @Size(max = 10000)
    private String content;
    private String createdBy;
    private LocalDateTime creationTime;

    public Question(String title, String content) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.creationTime = LocalDateTime.now();
    }

}
