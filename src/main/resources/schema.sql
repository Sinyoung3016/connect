CREATE TABLE if not exists user_pictures
(
    picture_id BINARY(16) PRIMARY KEY,
    file_path  VARCHAR(100) NOT NULL
);

CREATE TABLE if not exists users
(
    user_id      BINARY(16) PRIMARY KEY,
    picture_id   BINARY(16)  NOT NULL,
    username     VARCHAR(20) NOT NULL,
    password     VARCHAR(20) NOT NULL,
    email        VARCHAR(50) NOT NULL,
    student_id   VARCHAR(20) NOT NULL,
    sex          VARCHAR(10) NOT NULL,
    department   VARCHAR(50) NOT NULL,
    introduction VARCHAR(100) DEFAULT NULL,
    created_at   DATETIME(6) NOT NULL,
    updated_at   DATETIME(6)  DEFAULT NULL,
    FOREIGN KEY (picture_id) REFERENCES user_pictures (picture_id)
);

CREATE TABLE if not exists matching_groups
(
    group_id      BINARY(16) PRIMARY KEY,
    owner_id      BINARY(16)  NOT NULL,
    title         VARCHAR(30) NOT NULL,
    subtitle      VARCHAR(50) DEFAULT NULL,
    interest_tag  VARCHAR(20) NOT NULL,
    num_of_member INT         NOT NULL,
    created_at    DATETIME(6) NOT NULL,
    updated_at    DATETIME(6) DEFAULT NULL
);

CREATE TABLE if not exists group_users
(
    group_user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    group_id      BINARY(16) NOT NULL,
    user_id       BINARY(16) NOT NULL,
    FOREIGN KEY (group_id) REFERENCES matching_groups (group_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE if not exists request_connections
(
    request_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    group_id_from BINARY(16) NOT NULL,
    group_id_to   BINARY(16) NOT NULL,
    FOREIGN KEY (group_id_from) REFERENCES matching_groups (group_id) ON DELETE CASCADE,
    FOREIGN KEY (group_id_to) REFERENCES matching_groups (group_id) ON DELETE CASCADE
);

CREATE TABLE if not exists connections
(
    connection_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    group_id1     BINARY(16) NOT NULL,
    group_id2     BINARY(16) NOT NULL,
    FOREIGN KEY (group_id1) REFERENCES matching_groups (group_id) ON DELETE CASCADE,
    FOREIGN KEY (group_id2) REFERENCES matching_groups (group_id)
);