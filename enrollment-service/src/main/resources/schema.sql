CREATE TABLE enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    UNIQUE (course_id, student_id)  -- Unique constraint to prevent duplicate enrollments
);
