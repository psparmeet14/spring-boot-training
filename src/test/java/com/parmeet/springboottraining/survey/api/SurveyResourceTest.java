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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static final String SPECIFIC_QUESTION_URL = "http://localhost:8080/api/v1/surveys/2/questions/5";

    @Test
    void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
        var requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

        var mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
        var requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

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

        var mvcResult = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), true);
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}