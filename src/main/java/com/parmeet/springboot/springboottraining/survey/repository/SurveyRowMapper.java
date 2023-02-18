package com.parmeet.springboot.springboottraining.survey.repository;

import com.parmeet.springboot.springboottraining.survey.model.Question;
import com.parmeet.springboot.springboottraining.survey.model.Survey;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SurveyRowMapper implements RowMapper<Survey> {
    @Override
    public Survey mapRow(ResultSet rs, int rowNum) throws SQLException {
        Survey survey = new Survey();
        survey.setId(rs.getString("ID"));
        survey.setTitle(rs.getString("TITLE"));
        survey.setDescription(rs.getString("DESCRIPTION"));
        survey.setQuestions(List.of(new Question(rs.getString("QUESTIONS"), "test", List.of("test"), "test")));
        return survey;
    }
}
