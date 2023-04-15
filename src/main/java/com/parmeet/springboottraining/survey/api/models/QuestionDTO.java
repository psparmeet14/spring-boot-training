package com.parmeet.springboottraining.survey.api.models;

import java.util.List;

public record QuestionDTO(
        int id,
        String name,
        List<String> options,
        String correctAnswer
) {
}
