package com.rippling.test.data;

import com.rippling.test.exception.QuestionNotFoundException;
import com.rippling.test.model.Question;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionRepository {

    Map<String, Question> questions;

    public QuestionRepository() {
        this.questions = new HashMap<>();
    }

    public Question save(Question question){
        questions.put(question.getId(),question);
        return question;
    }

    public List<Question> getAllQuestions(){
        return questions.values().stream().toList();
    }

    public Question getQuestionById(String id){
        if(!questions.containsKey(id)){
            throw new QuestionNotFoundException(id);
        }
        return questions.get(id);
    }

    public void upVote(String id){
        if(!questions.containsKey(id)){
            throw new QuestionNotFoundException(id);
        }
        questions.get(id).incrementVoteCount();
    }
}
