package com.dins.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Data
@Table(name = "contact")
public class Contact {

    private String name;

    private String phoneNumber;

    @ManyToOne
    PhoneBook phoneBook;

    @Id
    private Long id;

}
