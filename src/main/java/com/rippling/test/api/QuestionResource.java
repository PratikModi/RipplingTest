package com.rippling.test.api;

import com.rippling.test.exception.QuestionNotFoundException;
import com.rippling.test.model.Question;
import com.rippling.test.service.IQuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
    @ApiOperation(value="", nickname = "Post Questions", notes = "API to Post the questions", tags = {"postQuestions"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/v1/questions")
    public ResponseEntity<Question> postQuestions(@RequestBody @NonNull @Valid Question question,@RequestHeader(name = "userId", required = true) String userId){
        question.setCreatedBy(userId);
        System.out.println(question);
        Question postedQuestion = questionService.postQuestion(question);
        String location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postedQuestion.getId())
                .toUriString();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION,location).body(postedQuestion);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/v1/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable @NotNull String id){
        Question question=null;
        try {
            question = questionService.getQuestion(id);

        }catch(Exception e){
            throw new QuestionNotFoundException(id);
        }
        return ResponseEntity.ok(question);
    }

    @RequestMapping( method = RequestMethod.GET, value = "/v1/questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        //Talk about Pagination here
        //Use Paging and Sorting Repository -- PagingAndSortingRepository
        // It takes Pageable instance
        //Pageable firstTwo = PageRequest.of(0,2); PageNumber and PageSize
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @RequestMapping(value = "/v1/questions/{id}/votes", method = RequestMethod.GET)
    public ResponseEntity<Integer> getVotes(@PathVariable @NotNull String id){
        return ResponseEntity.ok(questionService.getVoteCount(id));

    }

    @RequestMapping(method = RequestMethod.POST, value = "/v1/questions/{id}/votes")
    public ResponseEntity upVote(@PathVariable @NotNull String id, @RequestHeader(value = "userId", required = true) String userId) {
        if (questionService.hasUserVoted(id, userId)) {
            return ResponseEntity.badRequest().body("User already voted for question:" + id);
        } else{
            questionService.upVote(id, userId);
            return ResponseEntity.ok().build();
        }
    }

}
