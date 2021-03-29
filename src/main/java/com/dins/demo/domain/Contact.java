package com.dins.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.Digits;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Contact {
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
