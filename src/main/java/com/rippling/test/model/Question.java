package com.rippling.test.model;

import com.rippling.test.validator.Validator;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Question implements Validator{
    private String id;
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime creationTime;
    private ThreadLocal<Integer> voteCount;

    public Question(String title, String content, String createdBy) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.creationTime = LocalDateTime.now();
        this.voteCount=ThreadLocal.withInitial(()->0);
    }

    public void incrementVoteCount(){
        this.voteCount.set(this.voteCount.get()+1);
    }

    @Override
    public boolean validate() {
        return StringUtils.hasLength(this.id) && StringUtils.hasLength(this.content) && StringUtils.hasLength(this.createdBy);
    }
}
