package com.emeritus.cms.enrollment_service.controller;

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

import com.emeritus.cms.enrollment_service.FeignClients.CourseServiceClient;
import com.emeritus.cms.enrollment_service.model.Enrollment;
import com.emeritus.cms.enrollment_service.service.EnrollmentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/enrollments")
@EnableMethodSecurity
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseServiceClient courseServiceClient;

    @PostMapping("/enroll")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<?> createEnrollment(@RequestBody Enrollment enrollment) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long studentId = (Long) authentication.getCredentials();

        enrollment.setStudentId(studentId);

        if(enrollmentService.isAlreadyEnrolled(enrollment.getCourseId(), studentId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Enrolled !");
        }
        
        //Verify wheteher course exists or not? by calling Course Service
        //if course not found
        if(!courseServiceClient.doesCourseExistsById(enrollment.getCourseId())){
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        Enrollment savedEnrollment = enrollmentService.enrollInCourse(enrollment);

        return new ResponseEntity<>(savedEnrollment, HttpStatus.CREATED);
    }

    @GetMapping("/my-enrollments")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<List<Enrollment>> getMyEnrollments() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getCredentials().toString();
        Long studentId = 1L;
        try {
            studentId = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        List<Enrollment> enrollments = enrollmentService.findEnrollmentsByStudent(studentId);
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.findAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@PathVariable Long id) {
        List<Enrollment> enrollments = enrollmentService.findEnrollmentsByStudent(id);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("course/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<?> getEnrollmentsByCourse(@PathVariable Long id) {

        //Verify wheteher course exists or not? by calling Course Service
        //if course not found
        if(!courseServiceClient.doesCourseExistsById(id)){
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        List<Enrollment> enrollments = enrollmentService.findEnrollmentsByCourse(id);
        return ResponseEntity.ok(enrollments);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEnrollment(@PathVariable Long id) {

        Optional<Enrollment> enrollOpt = enrollmentService.findEnrollmentById(id);
        if (!enrollOpt.isPresent()) {
            return new ResponseEntity<>("Enrollment not found", HttpStatus.NOT_FOUND);
        }

        enrollmentService.deleteEnrollment(id);
        return new ResponseEntity<>("Enrollment deleted", HttpStatus.NO_CONTENT);
    }
}
