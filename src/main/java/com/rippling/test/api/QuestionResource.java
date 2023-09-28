package com.rippling.test.api;

import com.rippling.test.exception.InvalidInputException;
import com.rippling.test.exception.QuestionNotFoundException;
import com.rippling.test.model.Question;
import com.rippling.test.service.IQuestionService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
public class QuestionResource {

    private final IQuestionService questionService;
    @Autowired
    public QuestionResource(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/v1/questions")
    public ResponseEntity<Question> postQuestion(@RequestBody @NonNull Question question){
        if(!question.validate()){
            throw new InvalidInputException(question.toString());
        }
        Question postedQuestion = questionService.postQuestion(question);
        String location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postedQuestion.getId())
                .toUriString();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION,location).body(postedQuestion);
    }
    @RequestMapping(value = "/v1/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable @NotNull String id){
        Question question=null;
        try {
            question = questionService.getQuestion(id);

        }catch(Exception e){
            throw new QuestionNotFoundException(id);
        }
        return ResponseEntity.ok(question);
    }

    @RequestMapping(value = "/v1/questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

}
