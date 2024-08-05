package com.rippling.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Validated
public class Question {
    private String id;
    @NotNull(message = "Title is required field")
    @Size(max = 250)
    @JsonProperty
    @Valid
    private String title;
    @NotNull(message = "Content is required field")
    @JsonProperty
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
