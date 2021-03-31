package com.dins.demo.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Contact extends RepresentationModel<Contact> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String contactname;

    private String phone;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore
    User user;
}
