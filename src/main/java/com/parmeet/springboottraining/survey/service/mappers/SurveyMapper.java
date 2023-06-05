package com.parmeet.springboottraining.survey.service.mappers;

import com.parmeet.springboottraining.survey.api.models.SurveyDTOV1;
import com.parmeet.springboottraining.survey.repository.models.Survey;

public class SurveyMapper {
    public static SurveyDTOV1 mapToDTO(Survey survey) {
        return new SurveyDTOV1(
                survey.getId(),
                survey.getTitle(),
                survey.getDescription(),
                survey.getQuestions().stream().map(QuestionMapper::mapToDTO).toList()
        );
    }

    public static Survey mapFromDTO(SurveyDTOV1 surveyDTOV1) {
        return new Survey(
                surveyDTOV1.getId() != null ? surveyDTOV1.getId() : 0,
                surveyDTOV1.getTitle(),
                surveyDTOV1.getDescription(),
                surveyDTOV1.getQuestions().stream().map(QuestionMapper::mapFromDTO).toList()
        );
    }
}
