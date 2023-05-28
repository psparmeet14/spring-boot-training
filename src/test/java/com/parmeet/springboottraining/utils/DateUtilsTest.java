package com.parmeet.springboottraining.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    @Test
    void dateUtils_fromDate() {
        var date = new Date();
        var localDate = DateUtils.fromDate(date);

        assertEquals(date.getDate(), localDate.getDayOfMonth());
    }
}