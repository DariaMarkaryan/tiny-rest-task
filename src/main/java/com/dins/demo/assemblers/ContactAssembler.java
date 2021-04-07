package com.dins.demo.assemblers;

import com.dins.demo.entites.Contact;
import com.dins.demo.entites.ContactModel;
import com.dins.demo.services.UserService;
import com.dins.demo.web.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContactAssembler
        extends RepresentationModelAssemblerSupport<Contact, ContactModel> {

    @Autowired
    private UserService userService;

    public ContactAssembler() {
        super(UserController.class, ContactModel.class);
    }

    @Override
    public ContactModel toModel(Contact entity) {
        ContactModel model = instantiateModel(entity);
        model.setUser_id(entity.getUser().getId());
        model.setContactname(entity.getContactname());
        model.setPhone(entity.getPhone());
        return model;
    }

    public Contact getContactFromContactModel(ContactModel model) {
        Contact contact = new Contact();
        contact.setUser(userService.getUserById(model.getUser_id()));
        contact.setPhone(model.getPhone());
        contact.setContactname(model.getContactname());
        return contact;
    }
}
