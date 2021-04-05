CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(25) NOT NULL
    );


CREATE TABLE IF NOT EXISTS contact (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    contactname varchar(25) NOT NULL,
    phone varchar(25) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
    );