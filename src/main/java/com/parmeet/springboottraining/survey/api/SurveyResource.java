package com.parmeet.springboottraining.survey.api;

import com.parmeet.springboottraining.survey.repository.models.Question;
import com.parmeet.springboottraining.survey.repository.models.Survey;
import com.parmeet.springboottraining.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/{surveyId}/questions")
    public List<Question> retrieveAllSurveyQuestions(@PathVariable int surveyId) {
        var questions = surveyService.retrieveAllSurveyQuestions(surveyId);
        if (questions == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return questions;
    }

    @GetMapping("/{surveyId}/questions/{questionId}")
    public Question retrieveSpecificSurveyQuestion(@PathVariable int surveyId, @PathVariable int questionId) {
        var question = surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId);
        if (question == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return question;
    }

    @PostMapping("/{surveyId}/questions")
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable int surveyId, @RequestBody Question question) {
        var questionId = surveyService.addNewSurveyQuestion(surveyId, question);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{questionId}")
                .buildAndExpand(questionId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> updateSurveyQuestion(@PathVariable int surveyId, @PathVariable int questionId,
                                                       @RequestBody Question question) {
        surveyService.updateSurveyQuestion(surveyId, questionId, question);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable int surveyId, @PathVariable int questionId) {
        surveyService.deleteSurveyQuestion(surveyId, questionId);
        return ResponseEntity.noContent().build();
    }
}
