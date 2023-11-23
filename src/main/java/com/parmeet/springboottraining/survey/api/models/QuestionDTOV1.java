package com.parmeet.springboottraining.survey.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//@JsonFilter("SurveyQuestionFilter")
public class QuestionDTOV1 {
    private Integer id;
    @NotBlank
    @JsonProperty("question_name")
    private String name;
    @NotEmpty
    private List<String> options;
    @NotBlank
    private String correctAnswer;
}
