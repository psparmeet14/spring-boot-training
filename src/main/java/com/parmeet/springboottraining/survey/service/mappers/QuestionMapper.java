package com.parmeet.springboottraining.survey.service.mappers;

import com.parmeet.springboottraining.survey.api.models.QuestionDTOV1;
import com.parmeet.springboottraining.survey.repository.models.Question;

public class QuestionMapper {

    public static QuestionDTOV1 mapToDTO(Question question) {
        return new QuestionDTOV1(
                question.getId(),
                question.getName(),
                question.getOptions(),
                question.getCorrectAnswer()
        );
    }

    public static Question mapFromDTO(QuestionDTOV1 questionDTOV1) {
        return new Question(
                questionDTOV1.getId() != null ? questionDTOV1.getId() : 0,
                0,
                questionDTOV1.getName(),
                questionDTOV1.getOptions(),
                questionDTOV1.getCorrectAnswer()
        );
    }
}
