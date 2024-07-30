package com.emeritus.cms.courseservice.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.emeritus.cms.courseservice.model.Course;
import com.emeritus.cms.courseservice.security.JwtUtil;
import com.emeritus.cms.courseservice.service.CourseService;
import com.emeritus.cms.courseservice.util.ErrorResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/courses")
@EnableMethodSecurity
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<String> createCourse(@RequestBody Course course, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        // Need to fetch Instructor ID from Token not as input
        System.out.println(authorizationHeader);

        String userId = jwtUtil.getUserIdFromAuthHeader(authorizationHeader);
        String instructorId = Long.toString(course.getInstructorId());

        if (!userId.equals(instructorId)) {
            return new ResponseEntity<String>("Can't create course with different Instructor Id", HttpStatus.CONFLICT);
             // throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Can't create course with different Instructor Id");
        }
        // return courseService.createCourse(course);
        return ResponseEntity.ok("Course Created Successfully !");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR') or hasAuthority('ROLE_STUDENT')")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/creator/{creatorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR') or hasAuthority('ROLE_STUDENT')")
    public List<Course> getCoursesByCreator(@PathVariable Long creatorId) {
        return courseService.getCoursesByCreator(creatorId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public Course updateCourse(@RequestBody Course course) {
        // Write logic to update only his course
        // if(course.getInstructorId() == getId from Token){
        return courseService.updateCourse(course);
        // }
        // else{
        // throw new Exception("Only the Creator is allowed to Update the course");
        // }
    }
}
