package com.emeritus.cms.enrollment_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeritus.cms.enrollment_service.model.Enrollment;
import com.emeritus.cms.enrollment_service.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment enrollInCourse(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public boolean isAlreadyEnrolled(Long courseId, Long studentId) {
        return enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId); // Enrollment already exists or not
    }

    public List<Enrollment> findEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findAllByStudentId(studentId);
    }

    public List<Enrollment> findEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findAllByCourseId(courseId);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    public List<Enrollment> findAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findEnrollmentById(Long id) {
       return enrollmentRepository.findById(id);
    }

}
