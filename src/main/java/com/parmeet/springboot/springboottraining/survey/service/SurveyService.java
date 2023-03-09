package com.parmeet.springboot.springboottraining.survey.service;

import com.parmeet.springboot.springboottraining.survey.model.Survey;
import com.parmeet.springboot.springboottraining.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;


    public List<Survey> retrieveAllSurveys() {
        List<Survey> surveys = surveyRepository.retrieveAllSurveys();

        return surveys;
    }
}
