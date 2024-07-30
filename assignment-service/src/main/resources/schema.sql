CREATE TABLE assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    submission_url VARCHAR(255) NOT NULL
);