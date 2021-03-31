package com.dins.demo.assemblers;

import com.dins.demo.entites.User;
import com.dins.demo.entites.UserModel;
import com.dins.demo.web.UserController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler
        extends RepresentationModelAssemblerSupport<User, UserModel> {

    public UserAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User entity) {
        UserModel model = instantiateModel(entity);
        model.add(linkTo(methodOn(UserController.class)
                .getUserById(entity.getId()))
                .withSelfRel());
        model.add(linkTo(
                methodOn(UserController.class)
                        .getAllContacts(entity.getId()))
                .withRel("phonebook"));
        model.setId(entity.getId());
        model.setName(entity.getName());
        return model;
    }
}
