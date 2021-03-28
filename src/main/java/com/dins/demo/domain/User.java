package com.dins.demo.domain;

import com.dins.demo.domain.PhoneBook;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToOne
    private PhoneBook phoneBook;
}
