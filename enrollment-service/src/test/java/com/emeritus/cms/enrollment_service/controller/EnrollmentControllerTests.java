package com.emeritus.cms.enrollment_service.controller;

import com.emeritus.cms.enrollment_service.model.Enrollment;
import com.emeritus.cms.enrollment_service.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class EnrollmentControllerTests {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    public EnrollmentControllerTests() {
        MockitoAnnotations.openMocks(this);
    }

    // @Testx
    // @WithMockUser(roles = "STUDENT")
    // public void testCreateEnrollment() {
    //     Enrollment enrollment = new Enrollment();
    //     when(enrollmentService.enrollInCourse(enrollment)).thenReturn(true);

    //     ResponseEntity<String> response = enrollmentController.createEnrollment(enrollment);
    //     assertEquals(HttpStatus.CREATED, response.getStatusCode());
    //     assertEquals("Enrollment created successfully", response.getBody());
    // }

    @Test
    @WithMockUser(roles = "STUDENT")
    public void testGetMyEnrollments() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentService.findEnrollmentsByStudent(anyLong())).thenReturn(Collections.singletonList(enrollment));

        ResponseEntity<List<Enrollment>> response = enrollmentController.getMyEnrollments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

        public ResponseEntity<List<Enrollment>> getMyEnrollments() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long studentId = (Long) authentication.getCredentials();

        List<Enrollment> enrollments = enrollmentService.findEnrollmentsByStudent(studentId);
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllEnrollments() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentService.findAllEnrollments()).thenReturn(Collections.singletonList(enrollment));

        ResponseEntity<List<Enrollment>> response = enrollmentController.getAllEnrollments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteEnrollment() {
        Long enrollmentId = 1L;
        when(enrollmentService.findEnrollmentById(enrollmentId)).thenReturn(Optional.of(new Enrollment()));

        ResponseEntity<String> response = enrollmentController.deleteEnrollment(enrollmentId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Enrollment deleted", response.getBody());
    }
}
