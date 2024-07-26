package com.emeritus.cms.enrollment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emeritus.cms.enrollment_service.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

}
