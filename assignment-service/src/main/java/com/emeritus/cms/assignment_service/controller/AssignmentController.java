package com.emeritus.cms.assignment_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeritus.cms.assignment_service.model.Assignment;
import com.emeritus.cms.assignment_service.service.AssignmentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/assignments")
@EnableMethodSecurity
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }

    // @GetMapping("/{id}")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR') or hasAuthority('ROLE_STUDENT')")
    // public Assignment getAssignment(@PathVariable Long id) {
    //     return assignmentService.getAssignment(id);
    // }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public List<Assignment> getAssignmentsByCourse(@PathVariable Long courseId) {
        //Need to check if student is enrolled for this course
        return assignmentService.getAssignmentsByCourse(courseId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public Assignment updateAssignment(@RequestBody Assignment assignment) {

        //Need to check if the student is enrolled for this course
        //Check if he has enrolled for the course using user_id, course_id and assignment_id
        return assignmentService.updateAssignment(assignment);
    }
}
