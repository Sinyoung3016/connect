CREATE TABLE if not exists users
(
    user_id      BINARY(16)   PRIMARY KEY,
    username     VARCHAR(20)  NOT NULL,
    password     VARCHAR(20)  NOT NULL,
    email        VARCHAR(50)  NOT NULL,
    student_id   VARCHAR(20)  NOT NULL,
    sex          VARCHAR(10)  NOT NULL,
    department   VARCHAR(20)  NOT NULL,
    introduction VARCHAR(100) DEFAULT NULL,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6)  DEFAULT NULL
);

CREATE TABLE if not exists user_pictures
(
    picture_id   BINARY(16)   PRIMARY KEY,
    user_id      BINARY(16)   NOT NULL,
    file_name    VARCHAR(50)  NOT NULL,
    file_path    VARCHAR(50)  NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

