package com.parmeet.springboottraining.survey.api.models;

import java.util.List;

public record QuestionDTO(
        Integer id,
        String name,
        List<String> options,
        String correctAnswer
) {
}
