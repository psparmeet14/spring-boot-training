package com.parmeet.springboot.springboottraining.survey.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {

    private String id;
    private String title;
    private String description;
    private List<Question> questions;
}
