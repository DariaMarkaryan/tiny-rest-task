package com.dins.demo.assemblers;

import com.dins.demo.entites.Contact;
import com.dins.demo.entites.ContactModel;
import com.dins.demo.web.UserController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContactAssembler
        extends RepresentationModelAssemblerSupport<Contact, ContactModel> {

    public ContactAssembler() {
        super(UserController.class, ContactModel.class);
    }

    @Override
    public ContactModel toModel(Contact entity) {
        ContactModel model = instantiateModel(entity);
        model.setId(entity.getId());
        model.setContactname(entity.getContactname());
        model.setPhone(entity.getPhone());
        model.setUser(entity.getUser());
        return model;
    }
}
