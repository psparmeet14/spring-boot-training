package com.parmeet.springboottraining.utils;

import com.parmeet.springboottraining.survey.repository.models.Survey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DbUtilsTest {

    @Test
    void updateIfPresent_withData_shouldUpdate() {
        var survey = new Survey(0, "", "", List.of());

        DbUtils.updateIfPresent(survey::setTitle, "My survey");

        Assertions.assertEquals("My survey", survey.getTitle());
    }

    @Test
    void updateIfChanged_withData_shouldUpdate() {
        var survey = new Survey(0, "", "", List.of());

        DbUtils.updateIfChanged(survey::setTitle, "My title", survey::getTitle);

        Assertions.assertEquals("My title", survey.getTitle());
    }

}