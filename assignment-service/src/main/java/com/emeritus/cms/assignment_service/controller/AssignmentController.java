package com.emeritus.cms.assignment_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeritus.cms.assignment_service.model.Assignment;
import com.emeritus.cms.assignment_service.service.AssignmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @GetMapping
    public List<Assignment> getAssignments() {
        return assignmentService.getAllAssignments();
    }

    @PostMapping("/add")
    public Assignment addAssignment(@RequestBody Assignment assignment) {
        return assignmentService.addAssignment(assignment);
    }
    
}
