package com.parmeet.springboottraining.survey.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SurveyDTOV1 {
    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotEmpty
    private List<QuestionDTOV1> questions;
}
