package com.rippling.test.service;

import com.rippling.test.model.Question;

import java.util.List;

public interface IQuestionService {
    Question postQuestion(Question question);
    Question getQuestion(String id);
    List<Question> getAllQuestions();
}
