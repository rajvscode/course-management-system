package com.emeritus.cms.enrollment_service.service;

import com.emeritus.cms.enrollment_service.model.Enrollment;
import com.emeritus.cms.enrollment_service.repository.EnrollmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EnrollmentServiceTests {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    public EnrollmentServiceTests() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // public void testEnrollInCourse() {
    //     Enrollment enrollment = new Enrollment();
    //     when(enrollmentRepository.existsByCourseIdAndStudentId(anyLong(), anyLong())).thenReturn(false);

    //     boolean result = enrollmentService.enrollInCourse(enrollment);
    //     assertEquals(true, result);
    //     verify(enrollmentRepository, times(1)).save(enrollment);
    // }

    @Test
    public void testFindEnrollmentsByStudent() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.findAllByStudentId(anyLong())).thenReturn(Collections.singletonList(enrollment));

        List<Enrollment> result = enrollmentService.findEnrollmentsByStudent(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindEnrollmentsByCourse() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.findAllByCourseId(anyLong())).thenReturn(Collections.singletonList(enrollment));

        List<Enrollment> result = enrollmentService.findEnrollmentsByCourse(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteEnrollment() {
        Long enrollmentId = 1L;
        when(enrollmentRepository.existsById(enrollmentId)).thenReturn(true);

        enrollmentService.deleteEnrollment(enrollmentId);
        verify(enrollmentRepository, times(1)).deleteById(enrollmentId);
    }

    @Test
    public void testFindAllEnrollments() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.findAll()).thenReturn(Collections.singletonList(enrollment));

        List<Enrollment> result = enrollmentService.findAllEnrollments();
        assertEquals(1, result.size());
    }

    @Test
    public void testFindEnrollmentById() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollment));

        Optional<Enrollment> result = enrollmentService.findEnrollmentById(1L);
        assertEquals(true, result.isPresent());
    }
}