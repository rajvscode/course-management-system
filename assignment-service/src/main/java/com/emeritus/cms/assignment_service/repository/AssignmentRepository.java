package com.emeritus.cms.assignment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emeritus.cms.assignment_service.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long>{

}
