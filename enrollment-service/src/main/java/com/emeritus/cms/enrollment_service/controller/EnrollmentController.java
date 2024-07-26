package com.emeritus.cms.enrollment_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeritus.cms.enrollment_service.model.Enrollment;
import com.emeritus.cms.enrollment_service.service.EnrollmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @PostMapping("/register")
    public Enrollment register(@RequestBody Enrollment enrollment) {
        return enrollmentService.register(enrollment);
    }
}
