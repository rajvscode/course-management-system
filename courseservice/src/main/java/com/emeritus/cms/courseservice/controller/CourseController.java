package com.emeritus.cms.courseservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.emeritus.cms.courseservice.model.Course;
import com.emeritus.cms.courseservice.service.CourseService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/courses")
@EnableMethodSecurity
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long instructorId = (Long) authentication.getCredentials();

        course.setInstructorId(instructorId);
        Course savedCourse = courseService.createCourse(course);

        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR') or hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<List<Course>> getAllCourses() {

        List<Course> courses = courseService.findAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/my-courses")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<List<Course>> getMyCourses() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long instructorId = (Long) authentication.getCredentials();

        List<Course> courses = courseService.findCoursesByCreator(instructorId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {

        Optional<Course> courseOpt = courseService.findCourseById(id);
        if (!courseOpt.isPresent()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        courseService.deleteCourseById(id);
        return new ResponseEntity<>("Course deleted", HttpStatus.NO_CONTENT);
    }

    // Update an existing course
    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<?> updateCourse(@RequestBody Course courseDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long instructorId = (Long) authentication.getCredentials();

        Optional<Course> courseOpt = courseService.findCourseById(courseDetails.getId());
        if (!courseOpt.isPresent()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOpt.get();
        if (!course.getInstructorId().equals(instructorId)) {
            return new ResponseEntity<>("Access denied. Can be updated only by the creator", HttpStatus.FORBIDDEN);
        }

        course.setName(courseDetails.getName());
        course.setDescription(courseDetails.getDescription());
        Course updatedCourse = courseService.createCourse(course);

        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    // Get a specific course created by the current user
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR') or hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {

        Optional<Course> courseOpt = courseService.findCourseById(id);
        if (!courseOpt.isPresent()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOpt.get();

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/course/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR') or hasAuthority('ROLE_STUDENT')")
    public boolean doesCourseExistsById(@PathVariable Long id) {

        Optional<Course> courseOpt = courseService.findCourseById(id);
        return courseOpt.isPresent();
    }
}
