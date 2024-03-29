package com.parmeet.springboottraining.survey.repository;

import com.parmeet.springboottraining.survey.repository.models.Question;
import com.parmeet.springboottraining.survey.repository.models.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class SurveyRepository {

    private final NamedParameterJdbcOperations jdbc;

    public int addNewSurvey(Survey survey) {
        var keyHolder = new GeneratedKeyHolder();
        var sql = """
                INSERT INTO SURVEY (
                    TITLE,
                    DESCRIPTION
                )
                VALUES (
                    :title,
                    :description
                );
                """;
        var params = new MapSqlParameterSource()
                .addValue("title", survey.getTitle())
                .addValue("description", survey.getDescription());
        
        jdbc.update(sql, params, keyHolder);
        return keyHolder.getKey() != null ? (int) keyHolder.getKey() : 0;
    }

    public Optional<List<Survey>> retrieveAllSurveys() {
        var sql = """
                SELECT
                    SURVEY.ID AS SURVEY_ID,
                    SURVEY.TITLE,
                    SURVEY.DESCRIPTION,
                    QUESTION.ID AS QUESTION_ID,
                    QUESTION.NAME,
                    QUESTION.OPTIONS,
                    QUESTION.CORRECT_ANSWER
                FROM SURVEY
                LEFT JOIN QUESTION ON SURVEY.ID = QUESTION.SURVEY_ID;
                """;

        return Optional.ofNullable(
                jdbc.query(sql, rs -> {
                    Map<Integer, Survey> surveysById = new HashMap<>();
                    while (rs.next()) {
                        var surveyId = rs.getInt("SURVEY_ID");
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
                })
        );
    }

    public int addNewSurveyQuestion(int surveyId, Question question) {
        var keyHolder = new GeneratedKeyHolder();
        var sql = """
                INSERT INTO QUESTION (
                    SURVEY_ID,
                    NAME,
                    OPTIONS,
                    CORRECT_ANSWER
                )
                VALUES (
                    :surveyId,
                    :name,
                    :options,
                    :correctAnswer
                );
                """;

        var params = new MapSqlParameterSource()
                .addValue("surveyId", surveyId)
                .addValue("name", question.getName())
                .addValue("options", String.join(",", question.getOptions()))
                .addValue("correctAnswer", question.getCorrectAnswer());

        jdbc.update(sql, params, keyHolder);
        return keyHolder.getKey() != null ? (int) keyHolder.getKey() : 0;
    }

    public void updateSurveyQuestion(int surveyId, int questionId, Question question) {
        var sql = """
                UPDATE QUESTION 
                SET NAME = :name,
                    OPTIONS = :options,
                    CORRECT_ANSWER = :correctAnswer
                WHERE ID = :id AND SURVEY_ID = :surveyId;     
                """;
        jdbc.update(sql, Map.ofEntries(
                        Map.entry("id", questionId),
                        Map.entry("surveyId", surveyId),
                        Map.entry("name", question.getName()),
                        Map.entry("options", String.join(",", question.getOptions())),
                        Map.entry("correctAnswer", question.getCorrectAnswer())
                )
        );
    }

    public void deleteSurveyQuestion(int surveyId, int questionId) {
        var sql = "DELETE FROM QUESTION WHERE ID = :id AND SURVEY_ID = :surveyId";
        jdbc.update(sql, Map.of("id", questionId, "surveyId", surveyId));
    }

    private static Survey createSurvey(int surveyId, ResultSet rs) throws SQLException {
        return new Survey(
                surveyId,
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"),
                new ArrayList<>());
    }

    private static Question createQuestion(int surveyId, int questionId, ResultSet rs) throws SQLException {
        return new Question(
                questionId,
                surveyId,
                rs.getString("NAME"),
                List.of(rs.getString("OPTIONS").split(",")),
                rs.getString("CORRECT_ANSWER"));
    }


}
