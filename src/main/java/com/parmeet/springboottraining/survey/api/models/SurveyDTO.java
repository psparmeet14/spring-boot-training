package com.parmeet.springboottraining.survey.api.models;

import com.parmeet.springboottraining.survey.repository.models.Question;

import java.util.List;

public record SurveyDTO(
        int id,
        String title,
        String description,
        List<Question> questions
) {
}
