package com.dins.demo.controllers;

import com.dins.demo.data.ContactRepository;
import com.dins.demo.domain.Contact;
import com.dins.demo.exceptions.ContactNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping(produces = "application/json")
    public Iterable<Contact> allContacts() {
        return contactRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postContact(@RequestBody Contact contact) {
        contactRepository.save(contact);
    }

    @PutMapping(path = "/{contactId}")
    public void updateContact(@PathVariable("contactId") int id,
                              @RequestBody Contact contact) {
        try {
            Contact old = contactRepository.findById(id)
                    .orElseThrow(
                            () -> new ContactNotFoundException("couldn't find contact with id = " + contact.getId()));
            old.setContactname(contact.getContactname());
            old.setPhone(contact.getPhone());
        } catch (ContactNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @DeleteMapping(path = "/{contactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("contactId") int id) {
        contactRepository.deleteById(id);
    }

    @GetMapping(path = "/{contactId}", produces = "application/json")
    public Contact getContact(@PathVariable("contactId") int id) {
        try {
            return contactRepository.findById(id).orElseThrow(
                    () -> new ContactNotFoundException("couldn't find contact with id = " + id)
            );

        } catch (ContactNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

}
