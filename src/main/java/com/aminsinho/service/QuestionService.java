package com.aminsinho.service;


import com.aminsinho.iservice.QuestionServiceInterface;
import com.aminsinho.models.Question;
import com.aminsinho.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements QuestionServiceInterface {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}

