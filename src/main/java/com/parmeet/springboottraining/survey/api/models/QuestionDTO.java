package com.parmeet.springboottraining.survey.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionDTO {
    private Integer id;
    @NotBlank
    private String name;
    @NotEmpty
    private List<String> options;
    @NotBlank
    private String correctAnswer;
}
