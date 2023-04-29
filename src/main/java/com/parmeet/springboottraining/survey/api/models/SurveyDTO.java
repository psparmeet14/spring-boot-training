package com.parmeet.springboottraining.survey.api.models;

import java.util.List;

public record SurveyDTO(
        Integer id,
        String title,
        String description,
        List<QuestionDTO> questions
) {
}
