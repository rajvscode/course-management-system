package com.emeritus.cms.courseservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeritus.cms.courseservice.model.Course;
import com.emeritus.cms.courseservice.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public Course registerCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }
}
