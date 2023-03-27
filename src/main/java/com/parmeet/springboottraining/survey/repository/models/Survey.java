package com.parmeet.springboottraining.survey.repository.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {

    private int id;
    private String title;
    private String description;
    private List<Question> questions;
}
