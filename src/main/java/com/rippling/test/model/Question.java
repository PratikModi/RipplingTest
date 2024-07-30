package com.rippling.test.model;

import com.rippling.test.validator.Validator;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Question implements Validator{
    private String id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String createdBy;
    private LocalDateTime creationTime;

    public Question(String title, String content, String createdBy) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.creationTime = LocalDateTime.now();
    }

    @Override
    public boolean validate() {
        return StringUtils.hasLength(this.id) && StringUtils.hasLength(this.content) && StringUtils.hasLength(this.createdBy);
    }
}
