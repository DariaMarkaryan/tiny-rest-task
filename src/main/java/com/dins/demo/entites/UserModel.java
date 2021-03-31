package com.dins.demo.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserModel extends RepresentationModel<UserModel> {
    private int id;
    private String name;
}
