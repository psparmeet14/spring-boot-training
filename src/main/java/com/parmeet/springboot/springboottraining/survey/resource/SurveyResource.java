package com.parmeet.springboot.springboottraining.survey.resource;

import com.parmeet.springboot.springboottraining.survey.model.Survey;
import com.parmeet.springboot.springboottraining.survey.service.SurveyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SurveyResource {

    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys() {
        return surveyService.retrieveAllSurveys();
    }

}
