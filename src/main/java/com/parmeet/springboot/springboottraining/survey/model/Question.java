package com.parmeet.springboot.springboottraining.survey.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;
    private String name;
    private List<String> options;
    private String correctAnswer;
}
