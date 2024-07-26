package com.emeritus.cms.assignment_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeritus.cms.assignment_service.model.Assignment;
import com.emeritus.cms.assignment_service.repository.AssignmentRepository;

@Service
public class AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;

    public Assignment addAssignment(Assignment assignment){
        return assignmentRepository.save(assignment);
    }
    public List<Assignment> getAllAssignments(){
        return assignmentRepository.findAll();
    }
}
