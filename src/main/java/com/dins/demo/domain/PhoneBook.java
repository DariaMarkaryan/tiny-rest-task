package com.dins.demo.domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "phone_book")
public class PhoneBook {
    @Id
    private Long id;

    @OneToMany
    List<Contact> contacts;

    @OneToOne
    private User user;


}
