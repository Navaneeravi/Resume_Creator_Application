-- User Table
CREATE TABLE "user" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Personal Details Table
CREATE TABLE "personal_details" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
);

-- Work Experience Table
CREATE TABLE "work_experience" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_title VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    duration VARCHAR(255) NOT NULL,
    personal_details_id BIGINT NOT NULL,
    FOREIGN KEY (personal_details_id) REFERENCES "personal_details" (id) ON DELETE CASCADE
);

-- Education Table
CREATE TABLE "education" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    degree VARCHAR(255) NOT NULL,
    institution VARCHAR(255) NOT NULL,
    year_of_graduation VARCHAR(255) NOT NULL,
    personal_details_id BIGINT NOT NULL,
    FOREIGN KEY (personal_details_id) REFERENCES "personal_details" (id) ON DELETE CASCADE
);

-- Skill Table
CREATE TABLE "skill" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    skill_name VARCHAR(255) NOT NULL,
    personal_details_id BIGINT NOT NULL,
    FOREIGN KEY (personal_details_id) REFERENCES "personal_details" (id) ON DELETE CASCADE
);
