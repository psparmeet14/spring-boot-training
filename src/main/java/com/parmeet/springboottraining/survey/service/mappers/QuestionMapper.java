package com.parmeet.springboottraining.survey.service.mappers;

import com.parmeet.springboottraining.survey.api.models.QuestionDTO;
import com.parmeet.springboottraining.survey.repository.models.Question;

public class QuestionMapper {

    public static QuestionDTO mapToDTO(Question question) {
        return new QuestionDTO(
                question.getId(),
                question.getName(),
                question.getOptions(),
                question.getCorrectAnswer()
        );
    }

    public static Question mapFromDTO(QuestionDTO questionDTO) {
        return new Question(
                questionDTO.id(),
                0,
                questionDTO.name(),
                questionDTO.options(),
                questionDTO.correctAnswer()
        );
    }
}
