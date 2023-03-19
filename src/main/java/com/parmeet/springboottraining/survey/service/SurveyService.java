package com.parmeet.springboottraining.survey.service;

import com.parmeet.springboottraining.survey.model.Survey;
import com.parmeet.springboottraining.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;


    public List<Survey> retrieveAllSurveys() {
        return surveyRepository.retrieveAllSurveys();
    }
}
