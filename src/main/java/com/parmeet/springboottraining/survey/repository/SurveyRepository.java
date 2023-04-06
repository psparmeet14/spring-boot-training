package com.parmeet.springboottraining.survey.repository;

import com.parmeet.springboottraining.survey.repository.models.Question;
import com.parmeet.springboottraining.survey.repository.models.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class SurveyRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_QUERY = "SELECT SURVEY.ID AS SURVEY_ID, SURVEY.TITLE, SURVEY.DESCRIPTION," +
            " QUESTION.ID AS QUESTION_ID, QUESTION.NAME, QUESTION.OPTIONS, QUESTION.CORRECT_ANSWER" +
            " FROM SURVEY LEFT JOIN QUESTION ON SURVEY.ID = QUESTION.SURVEY_ID ";


    public List<Survey> retrieveAllSurveys() {
        return jdbcTemplate.query(SELECT_QUERY, rs -> {
            Map<Integer, Survey> surveysById = new HashMap<>();
            while (rs.next()) {
                int surveyId = rs.getInt("SURVEY_ID");
                var survey = surveysById.get(surveyId);
                if (survey == null) {
                    survey = createSurvey(surveyId, rs);
                    surveysById.put(survey.getId(), survey);
                }
                var questionId = rs.getInt("QUESTION_ID");
                if (questionId != 0) {
                    var question = createQuestion(surveyId, questionId, rs);
                    survey.getQuestions().add(question);
                }
            }
            return surveysById.values().stream().toList();
        });
    }

    public int addNewSurveyQuestion(int surveyId, Question question) {
        return 0;
    }

    public void updateSurveyQuestion(int surveyId, int questionId, Question question) {

    }

    public void deleteSurveyQuestion(int surveyId, int questionId) {


    }

    private static Question createQuestion(int surveyId, int questionId, ResultSet rs) throws SQLException {
        return new Question(questionId,
                rs.getString("NAME"),
                Arrays.stream(rs.getString("OPTIONS").split(",")).toList(),
                rs.getString("CORRECT_ANSWER"),
                surveyId);
    }

    private static Survey createSurvey(int surveyId, ResultSet rs) throws SQLException {
        return new Survey(surveyId,
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"),
                new ArrayList<>());
    }
}
