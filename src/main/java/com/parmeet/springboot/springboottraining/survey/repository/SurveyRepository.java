package com.parmeet.springboot.springboottraining.survey.repository;

import com.parmeet.springboot.springboottraining.survey.model.Question;
import com.parmeet.springboot.springboottraining.survey.model.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class SurveyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static List<Survey> surveys = new ArrayList<>();

    static {
        Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3", "Most Popular DevOps Tool",
                Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = Arrays.asList(question1, question2, question3);

        Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

        surveys.add(survey);
    }

    public List<Survey> retrieveAllSurveys() {
        return jdbcTemplate.query("SELECT * FROM SURVEY", new SurveyRowMapper());
    }
}
