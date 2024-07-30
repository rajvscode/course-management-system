package com.emeritus.cms.courseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emeritus.cms.courseservice.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByInstructorId(Long instructorId);
}
