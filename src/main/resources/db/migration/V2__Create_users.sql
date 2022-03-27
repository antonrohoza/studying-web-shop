create table users (
    id INT NOT NULL AUTO_INCREMENT,
    login VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(45) NOT NULL,
    salt VARCHAR(100) NOT NULL,
    Role VARCHAR(45) NOT NULL
);