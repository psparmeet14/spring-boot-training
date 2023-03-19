package com.parmeet.springboottraining.survey.repository;

import com.parmeet.springboottraining.survey.model.Question;
import com.parmeet.springboottraining.survey.model.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SurveyRepository {

    private static final String SELECT_QUERY = "SELECT SURVEY.ID AS SURVEY_ID, SURVEY.TITLE, SURVEY.DESCRIPTION," +
            " QUESTION.ID AS QUESTION_ID, QUESTION.NAME, QUESTION.OPTIONS, QUESTION.CORRECT_ANSWER" +
            " FROM SURVEY LEFT JOIN QUESTION ON SURVEY.ID = QUESTION.SURVEY_ID ";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Survey> retrieveAllSurveys() {
        return jdbcTemplate.query(SELECT_QUERY, rs -> {
            Map<Integer, Survey> surveysById = new HashMap<>();
            while (rs.next()) {
                int surveyId = rs.getInt("SURVEY_ID");
                Survey survey = surveysById.get(surveyId);
                if (survey == null) {
                    String title = rs.getString("TITLE");
                    String description = rs.getString("DESCRIPTION");
                    survey = new Survey(surveyId, title, description, new ArrayList<>());
                    surveysById.put(survey.getId(), survey);
                }

                int questionId = rs.getInt("QUESTION_ID");
                String name = rs.getString("NAME");
                if (name != null) {
                    List<String> options = Arrays.stream(rs.getString("OPTIONS").split(",")).toList();
                    String correctAnswer = rs.getString("CORRECT_ANSWER");
                    Question question = new Question(questionId, name, options, correctAnswer, surveyId);

                    survey.getQuestions().add(question);
                }

            }
            Collection<Survey> values = surveysById.values();
            return values.stream().toList();
        });
    }
}
