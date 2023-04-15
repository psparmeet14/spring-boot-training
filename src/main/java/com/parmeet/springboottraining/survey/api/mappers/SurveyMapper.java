package com.parmeet.springboottraining.survey.api.mappers;

import com.parmeet.springboottraining.survey.api.models.SurveyDTO;
import com.parmeet.springboottraining.survey.repository.models.Survey;

public class SurveyMapper {
    public static SurveyDTO mapToDTO(Survey survey) {
        return new SurveyDTO(
                survey.getId(),
                survey.getTitle(),
                survey.getDescription(),
                survey.getQuestions().stream().map(QuestionMapper::mapToDTO).toList()
        );
    }
}
