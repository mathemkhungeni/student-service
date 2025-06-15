CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id VARCHAR(255) NOT NULL UNIQUE,
    student_name VARCHAR(255) NOT NULL,
    grade VARCHAR(255),
    mobile_number VARCHAR(255),
    school_name VARCHAR(255)
);