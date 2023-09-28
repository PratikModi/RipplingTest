package com.rippling.test.service.impl;

import com.rippling.test.data.QuestionRepository;
import com.rippling.test.model.Question;
import com.rippling.test.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements IQuestionService {

    QuestionRepository questionRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question postQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question getQuestion(String id) {
        return questionRepository.getQuestionById(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.getAllQuestions();
    }
}
