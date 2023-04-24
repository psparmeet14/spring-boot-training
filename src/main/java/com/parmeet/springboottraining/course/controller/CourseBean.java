package com.parmeet.springboottraining.course.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CourseBean {

    private static final Logger logger = LoggerFactory.getLogger(CourseBean.class);

    public CourseBean(@Value("${helloMessage}") String helloMessage) {
        logger.info(helloMessage);
    }
}
