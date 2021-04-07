DROP TABLE IF EXISTS OAUTH_USERS;

CREATE TABLE OAUTH_USERS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    google_id VARCHAR(25) DEFAULT NULL,
    name VARCHAR(256) NOT NULL,
    email VARCHAR(256) DEFAULT NULL,
    refresh_token VARCHAR(4096) NOT NULL
);