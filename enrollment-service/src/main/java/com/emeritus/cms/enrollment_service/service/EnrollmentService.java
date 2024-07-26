package com.emeritus.cms.enrollment_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeritus.cms.enrollment_service.model.Enrollment;
import com.emeritus.cms.enrollment_service.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

    @Autowired
    EnrollmentRepository enrollmentRepository;

    public Enrollment register( Enrollment enrollment){
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments(){
        return enrollmentRepository.findAll();
    }

}
