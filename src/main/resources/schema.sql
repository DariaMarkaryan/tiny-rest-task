CREATE DATABASE newdb;
USE newdb;

CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(25) NOT NULL,
-    );

CREATE TABLE IF NOT EXISTS phone_book (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
    );

CREATE TABLE IF NOT EXISTS contact (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    contactname varchar(25) NOT NULL,
    phone varchar(25) NOT NULL,
    phonebook_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES phone_book(id)
    );