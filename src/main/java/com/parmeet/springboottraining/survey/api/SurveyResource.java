package com.parmeet.springboottraining.survey.api;

import com.parmeet.springboottraining.exception.NoSuchElementFoundException;
import com.parmeet.springboottraining.security.user.User;
import com.parmeet.springboottraining.survey.api.models.QuestionDTO;
import com.parmeet.springboottraining.survey.api.models.SurveyDTO;
import com.parmeet.springboottraining.survey.service.SurveyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
@Validated
public class SurveyResource {

    private final SurveyService surveyService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from our API");
    }

    @GetMapping("")
    public List<SurveyDTO> retrieveAllSurveys() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication using static call: " + authentication);
        System.out.println("Authentication name using static call: " + authentication.getName());

        return surveyService.retrieveAllSurveys();
    }

    @GetMapping("/{surveyId}")
    public SurveyDTO retrieveSurveyById(@PathVariable @Min(1) int surveyId, Principal principal) {
        System.out.println("User principal: " + principal);
        System.out.println("User principal name: " + principal.getName());

        SurveyDTO survey = surveyService.retrieveSurveyById(surveyId);
        if (survey == null)
            throw new NoSuchElementFoundException("Survey not found with id: " + surveyId);
        return survey;
    }

    @GetMapping("/{surveyId}/questions")
    public List<QuestionDTO> retrieveAllSurveyQuestions(@PathVariable int surveyId, Authentication authentication) {
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication name: " + authentication.getName());
        var userDetails = (User) authentication.getPrincipal();
        System.out.println("Authentication principal (userDetails): " + userDetails);
        System.out.println("Authentication principal (userDetails, user has authorities): " + userDetails.getAuthorities());

        var questions = surveyService.retrieveAllSurveyQuestions(surveyId);
        if (questions == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return questions;
    }

    @GetMapping("/{surveyId}/questions/{questionId}")
    public QuestionDTO retrieveSpecificSurveyQuestion(@PathVariable int surveyId, @PathVariable int questionId, HttpServletRequest request) {
        System.out.println("Request: " + request);
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request user Principal: " + request.getUserPrincipal());
        System.out.println("Request user Principal name: " + request.getUserPrincipal().getName());

        var question = surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId);
        if (question == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return question;
    }

    @PostMapping("/{surveyId}/questions")
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable int surveyId, @Valid @RequestBody QuestionDTO question) {
        var questionId = surveyService.addNewSurveyQuestion(surveyId, question);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{questionId}")
                .buildAndExpand(questionId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> updateSurveyQuestion(
            @PathVariable int surveyId, @PathVariable int questionId,
            @Valid @RequestBody QuestionDTO question) {
        surveyService.updateSurveyQuestion(surveyId, questionId, question);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable int surveyId, @PathVariable int questionId) {
        surveyService.deleteSurveyQuestion(surveyId, questionId);
        return ResponseEntity.noContent().build();
    }
}
