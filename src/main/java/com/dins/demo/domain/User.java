package com.dins.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany
    private List<Contact> contacts;
}
