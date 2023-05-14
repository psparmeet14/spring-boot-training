package com.parmeet.springboottraining.survey.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SurveyDTO {
    private Integer i;

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotEmpty
    private List<QuestionDTO> questions;
}
