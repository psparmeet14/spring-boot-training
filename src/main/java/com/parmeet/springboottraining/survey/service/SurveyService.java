package com.parmeet.springboottraining.survey.service;

import com.parmeet.springboottraining.survey.model.Survey;
import com.parmeet.springboottraining.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;


    public List<Survey> retrieveAllSurveys() {
        return surveyRepository.retrieveAllSurveys();
    }

    public Survey retrieveSurveyById(int surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.retrieveAllSurveys().stream()
                .filter(survey -> survey.getId() == surveyId)
                .findFirst();

        return optionalSurvey.orElse(null);
    }
}
