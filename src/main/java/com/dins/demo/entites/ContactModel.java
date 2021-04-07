package com.dins.demo.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContactModel extends RepresentationModel<ContactModel> {
    private int user_id;
    private String contactname;
    private String phone;
}
