package com.emeritus.cms.enrollment_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emeritus.cms.enrollment_service.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
    List<Enrollment> findAllByCourseId(Long courseId);

    List<Enrollment> findAllByStudentId(Long studentId);

    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
}
