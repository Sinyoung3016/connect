CREATE TABLE if not exists user_pictures
(
    picture_id   BINARY(16)   PRIMARY KEY,
    file_path    VARCHAR(100) NOT NULL
);

CREATE TABLE if not exists users
(
    user_id      BINARY(16)   PRIMARY KEY,
    picture_id   BINARY(16)   NOT NULL,
    username     VARCHAR(20)  NOT NULL,
    password     VARCHAR(20)  NOT NULL,
    email        VARCHAR(50)  NOT NULL,
    student_id   VARCHAR(20)  NOT NULL,
    sex          VARCHAR(10)  NOT NULL,
    department   VARCHAR(50)  NOT NULL,
    introduction VARCHAR(100) DEFAULT NULL,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6)  DEFAULT NULL,
    FOREIGN KEY (picture_id) REFERENCES user_pictures (picture_id)
);

