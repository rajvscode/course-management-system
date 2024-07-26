package com.emeritus.cms.enrollment_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long courseid;

    @Column(nullable = false)
    private Long studentid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseId) {
        this.courseid = courseId;
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentId) {
        this.studentid = studentId;
    }

    

}
