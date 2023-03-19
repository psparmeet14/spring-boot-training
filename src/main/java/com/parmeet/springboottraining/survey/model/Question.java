package com.parmeet.springboottraining.survey.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private int id;
    private String name;
    private List<String> options;
    private String correctAnswer;

    @JsonIgnore
    private int surveyId;
}
