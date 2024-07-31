package com.emeritus.cms.courseservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeritus.cms.courseservice.model.Course;
import com.emeritus.cms.courseservice.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> findCoursesByCreator(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }
}
