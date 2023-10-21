package com.parmeet.springboottraining.survey.api.web.v1;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.parmeet.springboottraining.exception.NoSuchElementFoundException;
import com.parmeet.springboottraining.security.user.User;
import com.parmeet.springboottraining.survey.api.models.QuestionDTOV1;
import com.parmeet.springboottraining.survey.api.models.SurveyDTOV1;
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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(
        name = "Survey controller",
        description = "Controller for handling data between Survey UI and user uploaded surveys.",
        externalDocs = @ExternalDocumentation(
                url = "https://www.google.com",
                description = "RESTful APIs with Spring Boot"
        )
)
@RestController
@RequestMapping(value = "/api/v1/surveys")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
public class SurveyResource {

    private final SurveyService surveyService;
    private final MessageSource messageSource;

    @GetMapping("/hello")
    @Hidden
    public ResponseEntity<String> sayHello() {
        var locale = LocaleContextHolder.getLocale();
        var message = messageSource.getMessage("good.morning.message", null, "Default message", locale);
        return ResponseEntity.ok("Hello from our API! " + message);
    }

    @GetMapping("")
    public List<SurveyDTOV1> retrieveAllSurveys() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        /* authentication = UsernamePasswordAuthenticationToken
         *  [
         *    Principal=User(id=0, firstName=Parmeet, lastName=Singh, email=psparmeet14@gmail.com, password=$2a$10$JSWcGgS.GE8mZrggkrDH1uT5jfmFYNGauVvJyn2FOt0bxXD4sYvEm, role=USER),
         *    Credentials=null,
         *    Authenticated=true,
         *    Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null],
         *    Granted Authorities=[USER]
         *  ]
        */
        System.out.println("Authentication using static call: " + authentication);
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
                                                    value = """
                                                            {
                                                              "timestamp": "2021-08-17T12:47:50.000+00:00",
                                                              "status": 403,
                                                              "error": "Forbidden",
                                                              "message": "Access Denied",
                                                              "path": "/api/v1/surveys/1"
                                                            }"""
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
    public EntityModel<SurveyDTOV1> retrieveSurveyById(
            @PathVariable @Min(1) int surveyId,
            Principal principal
    ) {
        System.out.println("User principal: " + principal);
        System.out.println("User principal name: " + principal.getName());
        /*
            principal = UsernamePasswordAuthenticationToken
            [
                Principal=User(id=0, firstName=Parmeet, lastName=Singh, email=psparmeet14@gmail.com, password=$2a$10$i7kZz.XMdrxIhz6.xx86MOVvmjUYP2DU68lJXP8w1oX0pL3KQf2s., role=USER),
                Credentials=[PROTECTED],
                Authenticated=true,
                Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null],
                Granted Authorities=[USER]
             ]

             principal.getName() = psparmeet14@gmail.com
         */

        // Add link to all users HATEOAS
        var surveyDTOV1Optional = surveyService.retrieveSurveyById(surveyId);
        if (surveyDTOV1Optional.isEmpty()) {
            throw new NoSuchElementFoundException("Survey not found with id: " + surveyId);
        }
        var entityModel = EntityModel.of(surveyDTOV1Optional.get());
        var link = linkTo(methodOn(this.getClass()).retrieveAllSurveys());
        entityModel.add(link.withRel("all-surveys"));

        return entityModel;
    }

    @GetMapping("/{surveyId}/questions")
    public MappingJacksonValue retrieveAllSurveyQuestions(
            @PathVariable int surveyId,
            Authentication authentication
    ) {
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication name: " + authentication.getName());
        var userDetails = (User) authentication.getPrincipal();
        System.out.println("Authentication principal (userDetails): " + userDetails);

        /*
            authentication= UsernamePasswordAuthenticationToken
            [Principal=User(id=0, firstName=Parmeet, lastName=Singh, email=psparmeet14@gmail.com, password=$2a$10$hYuQp/rdJbB9P302WnoS1eEU7VQqKxgoEhOW6U0mCT3LU1hrkcY16, role=USER),
             Credentials=[PROTECTED],
             Authenticated=true,
             Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null],
             Granted Authorities=[USER]
            ]

            authentication.getName() = psparmeet14@gmail.com

            (User) authentication.getPrincipal()
                    = User(id=0, firstName=Parmeet, lastName=Singh, email=psparmeet14@gmail.com, password=$2a$10$hYuQp/rdJbB9P302WnoS1eEU7VQqKxgoEhOW6U0mCT3LU1hrkcY16, role=USER)
         */

        var optionalQuestionDTOV1s = surveyService.retrieveAllSurveyQuestions(surveyId);
        if (optionalQuestionDTOV1s.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Dynamic property filtering
        var mappingJacksonValue = new MappingJacksonValue(optionalQuestionDTOV1s.get());
        var filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "question_name", "options");
        var filterProvider = new SimpleFilterProvider().addFilter("SurveyQuestionFilter", filter).setFailOnUnknownId(false);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping("/{surveyId}/questions/{questionId}")
    public QuestionDTOV1 retrieveSpecificSurveyQuestion(
            @PathVariable int surveyId,
            @PathVariable int questionId,
            HttpServletRequest request,
            @AuthenticationPrincipal User user
    ) {
        System.out.println("Request: " + request);
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("User user: " + user);

        /*
            request.getRequestURI() = /api/v1/surveys/1/questions/1

            user =  User(id=0, firstName=Parmeet, lastName=Singh, email=psparmeet14@gmail.com, password=$2a$10$sEWE.c5nPMAXWUXEWJYGuOqGgN5XCERUZ/Wa.8GalsBH2o9ng1Z2a, role=USER)
         */

        return surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> addNewSurvey(
            @Valid @RequestBody SurveyDTOV1 survey
    ) {
        var surveyId = surveyService.addNewSurvey(survey);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{surveyId}")
                .buildAndExpand(surveyId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{surveyId}/questions")
    public ResponseEntity<Object> addNewSurveyQuestion(
            @PathVariable int surveyId,
            @Valid @RequestBody QuestionDTOV1 question
    ) {
        var questionId = surveyService.addNewSurveyQuestion(surveyId, question);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{questionId}")
                .buildAndExpand(questionId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> updateSurveyQuestion(
            @PathVariable int surveyId,
            @PathVariable int questionId,
            @Valid @RequestBody QuestionDTOV1 question
    ) {
        surveyService.updateSurveyQuestion(surveyId, questionId, question);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> deleteSurveyQuestion(
            @PathVariable int surveyId,
            @PathVariable int questionId
    ) {
        surveyService.deleteSurveyQuestion(surveyId, questionId);
        return ResponseEntity.noContent().build();
    }
}
