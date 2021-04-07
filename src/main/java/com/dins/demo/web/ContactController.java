package com.dins.demo.web;

import com.dins.demo.assemblers.ContactAssembler;
import com.dins.demo.entites.Contact;
import com.dins.demo.entites.ContactModel;
import com.dins.demo.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactAssembler contactAssembler;

    @GetMapping(path = "/{contactId}", produces = "application/json")
    public ResponseEntity<Contact> getContactById(@PathVariable("contactId") int id) {
        Contact contact = contactService.getContact(id);
        if (contact != null) {
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Contact> postContact(@RequestBody ContactModel contact) {
        Contact saved = contactService.createContact(contactAssembler.getContactFromContactModel(contact));
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping(path = "/{contactId}")
    public ResponseEntity<Contact> updateContact(@PathVariable("contactId") int id,
                                                 @RequestBody ContactModel contact) {
        return contactService.updateContact(id, contact);
    }

//    public ResponseEntity<User> updateUser(@PathVariable("id") int id,
//                                           @RequestBody @Valid User user) throws UserNotFoundException {
//        return userService.updateUser(id, user);

    @DeleteMapping(path = "/{contactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("contactId") int id) {
        contactService.deleteContact(id);
    }

}
