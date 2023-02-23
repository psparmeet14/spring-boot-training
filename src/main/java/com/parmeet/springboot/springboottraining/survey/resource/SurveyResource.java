package com.parmeet.springboot.springboottraining.survey.resource;

import com.parmeet.springboot.springboottraining.survey.model.Survey;
import com.parmeet.springboot.springboottraining.survey.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/surveys")
public class SurveyResource {

    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from our API");
    }

    @GetMapping("/hello-string")
    public String sayHelloString() {
        return "Hello from our API string";
    }

    @RequestMapping("")
    public List<Survey> retrieveAllSurveys() {
        return surveyService.retrieveAllSurveys();
    }

}
