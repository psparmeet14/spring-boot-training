package com.parmeet.springboottraining.survey.api;

import com.parmeet.springboottraining.exception.NoSuchElementFoundException;
import com.parmeet.springboottraining.security.user.User;
import com.parmeet.springboottraining.survey.api.models.QuestionDTO;
import com.parmeet.springboottraining.survey.api.models.SurveyDTO;
import com.parmeet.springboottraining.survey.service.SurveyService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "Survey controller",
        description = "Controller for handling data between Survey UI and user uploaded surveys.",
        externalDocs = @ExternalDocumentation(
                url = "https://www.google.com",
                description = "Google"
        )
)
@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
public class SurveyResource {

    private final SurveyService surveyService;

    @GetMapping("/hello")
    @Hidden
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

    @Operation(
            summary = "This is summary for retrieve survey by id end point",
            description = "Get endpoint to Retrieve survey by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Survey retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Survey not found"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized / Invalid Token",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Invalid Token",
                                                    value = "{\n" +
                                                            "  \"timestamp\": \"2021-08-17T12:47:50.000+00:00\",\n" +
                                                            "  \"status\": 403,\n" +
                                                            "  \"error\": \"Forbidden\",\n" +
                                                            "  \"message\": \"Access Denied\",\n" +
                                                            "  \"path\": \"/api/v1/surveys/1\"\n" +
                                                            "}"
                                            )
                                    }
                            )
                    )
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            },
            tags = {
                    "Survey controller"
            },
            externalDocs = @ExternalDocumentation(
                    url = "https://www.google.com",
                    description = "Google"
            )
    )
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
