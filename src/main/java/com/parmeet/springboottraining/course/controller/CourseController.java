package com.parmeet.springboottraining.course.controller;

import com.parmeet.springboottraining.course.configuration.CourseServiceConfiguration;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class CourseController {

    @Autowired
    private CourseServiceConfiguration configuration;

    @RequestMapping("/course-configuration")
    public CourseServiceConfiguration getCourseServiceConfiguration() {
        return configuration;
    }
}
