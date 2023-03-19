package com.parmeet.springboottraining.survey.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyResourceIT {

    //http://localhost:RANDOM_PORT/surveys
    String str = """
             {
                "id": "SURVEY1",
                "title": "MY FIRST SURVEY",
                "description": "SURVEY DESCRIPTION",
                "questions": [
                  {
                    "id": "QUESTION1",
                    "name": "test",
                    "options": [
                      "test"
                    ],
                    "correctAnswer": "test"
                  }
                ]
              }
            """;


    @Autowired
    private TestRestTemplate template;

    @Test
    void retrieveAllSurveys_basicScenario() {
        var responseEntity = template.getForEntity("/surveys", List.class);
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());
    }



}