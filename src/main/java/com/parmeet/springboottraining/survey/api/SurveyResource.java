package com.parmeet.springboottraining.survey.api;

import com.parmeet.springboottraining.survey.model.Survey;
import com.parmeet.springboottraining.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyResource {

    private final SurveyService surveyService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from our API");
    }

    @GetMapping("")
    public List<Survey> retrieveAllSurveys() {
        return surveyService.retrieveAllSurveys();
    }

    @GetMapping("/{surveyId}")
    public Survey retrieveSurveyById(@PathVariable int surveyId) {
        Survey survey = surveyService.retrieveSurveyById(surveyId);
        if (survey == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return survey;
    }

}
