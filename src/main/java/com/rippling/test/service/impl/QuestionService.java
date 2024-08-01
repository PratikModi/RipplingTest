package com.rippling.test.service.impl;

import com.rippling.test.data.QuestionRepository;
import com.rippling.test.data.VoteRepository;
import com.rippling.test.model.Question;
import com.rippling.test.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements IQuestionService {

    QuestionRepository questionRepository;
    VoteRepository voteRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository,VoteRepository voteRepository) {
        this.questionRepository = questionRepository;
        this.voteRepository= voteRepository;
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

    @Override
    public void upVote(String questionId, String userId) {
        voteRepository.addVote(questionId, userId);
    }

    @Override
    public Integer getVoteCount(String questionId) {
        return voteRepository.totalVotes(questionId);
    }
}
