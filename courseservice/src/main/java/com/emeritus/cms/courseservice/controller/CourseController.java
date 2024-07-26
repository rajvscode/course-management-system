package com.emeritus.cms.courseservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeritus.cms.courseservice.model.Course;
import com.emeritus.cms.courseservice.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public List<Course> getCourses() {
        return courseService.findAllCourses();
    }

    @PostMapping("/register")
    public Course registerCourse(@RequestBody Course course) {
        return courseService.registerCourse(course);
    }
}
