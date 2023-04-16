package com.parmeet.springboottraining.survey.api;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyResourceIT {

    @Autowired
    private TestRestTemplate template;

    private static final String ALL_SURVEYS_URL = "/api/v1/surveys";
    private static final String SPECIFIC_SURVEY_URL = "/api/v1/surveys/2";
    private static final String ALL_SURVEY_QUESTIONS_URL = "/api/v1/surveys/2/questions";
    private static final String SPECIFIC_QUESTION_URL = "/api/v1/surveys/2/questions/5";

    @Test
    void retrieveAllSurveys_basicScenario() {
        // TODO
    }

    @Test
    void retrieveSurveyById_basicScenario() {
        // TODO
    }

    @Test
    void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
        var expectedResponse = """
                [
                    {
                        "id": 4,
                        "name": "Most Popular programming language"
                    },
                    {
                        "id": 5,
                        "name": "Best programming language for Machine Learning"
                    },
                    {
                        "id": 6,
                        "name": "Most Popular UI framework"
                    }
                ]      
                """;

        var responseEntity = template.getForEntity(ALL_SURVEY_QUESTIONS_URL, String.class);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
        var expectedResponse = """
                {
                    "id":5,
                    "name":"Best programming language for Machine Learning",
                    "options":["Java","GoLang","Python","JavaScript"],
                    "correctAnswer":"Python"
                }
                """;

        var responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
    }

    @Test
    void addNewSurveyQuestion_basicScenario() {
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
        var headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var httpEntity = new HttpEntity<>(requestBody, headers);

        var responseEntity = template.exchange(ALL_SURVEY_QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        var locationHeader = responseEntity.getHeaders().get("Location").get(0);
        assertTrue(locationHeader.contains(ALL_SURVEY_QUESTIONS_URL));
        template.delete(locationHeader);
    }

}
