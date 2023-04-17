package com.parmeet.springboottraining.survey.api;

import com.parmeet.springboottraining.security.configuration.JwtAuthFilter;
import com.parmeet.springboottraining.survey.api.models.QuestionDTO;
import com.parmeet.springboottraining.survey.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
class SurveyResourceTest {

    @MockBean
    private SurveyService surveyService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private MockMvc mockMvc;

    private static final String ALL_SURVEY_QUESTIONS_URL = "http://localhost:8080/api/v1/surveys/2/questions";
    private static final String SPECIFIC_QUESTION_URL = "http://localhost:8080/api/v1/surveys/2/questions/5";

    @Test
    void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        var mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
        var expectedResponse = """
                {
                    "id":5,
                    "name":"Best programming language for Machine Learning",
                    "options":["Java","GoLang","Python","JavaScript"],
                    "correctAnswer":"Python"
                }
                """;
        var questionDTO = new QuestionDTO(
                5,
                "Best programming language for Machine Learning",
                Arrays.asList("Java", "GoLang", "Python", "JavaScript"),
                "Python"
        );
        when(surveyService.retrieveSpecificSurveyQuestion(2, 5)).thenReturn(questionDTO);

        var requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        var mvcResult = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), true);
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void addNewSurveyQuestion_basicScenario() throws Exception {
        var requestBody = """
                {
                    "name": "Best programming language for Web Applications",
                    "options": [
                        "Java",
                        "GoLang",
                        "Python",
                        "JavaScript"
                    ],
                    "correctAnswer": "Java"
                }
                """;
        when(surveyService.addNewSurveyQuestion(anyInt(), any())).thenReturn(10);

        var requestBuilder = MockMvcRequestBuilders.post(ALL_SURVEY_QUESTIONS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody).contentType(MediaType.APPLICATION_JSON);
        var mvcResult = mockMvc.perform(requestBuilder).andReturn();

        var response = mvcResult.getResponse();
        var location = response.getHeader("Location");

        assertEquals(201, response.getStatus());
        assertTrue(Objects.requireNonNull(location).contains("/surveys/2/questions/10"));
    }
}