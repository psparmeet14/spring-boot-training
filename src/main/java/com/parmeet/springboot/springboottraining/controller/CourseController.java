package com.parmeet.springboot.springboottraining.controller;

import com.parmeet.springboot.springboottraining.configuration.CourseServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @Autowired
    private CourseServiceConfiguration configuration;

    @RequestMapping("/course-configuration")
    public CourseServiceConfiguration getCourseServiceConfiguration() {
        return configuration;
    }
}
