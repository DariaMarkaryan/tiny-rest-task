package com.dins.demo.controllers;

import com.dins.demo.data.ContactRepository;
import com.dins.demo.domain.Contact;
import com.dins.demo.domain.User;
import com.dins.demo.exceptions.ContactNotFoundException;
import com.dins.demo.services.ContactService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping(path = "/{contactId}", produces = "application/json")
    public Contact getContact(@PathVariable("contactId") int id) throws NotFoundException {
        return contactService.getContact(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postContact(@RequestBody Contact contact) {
        contactService.createContact(contact);
    }

    @PutMapping(path = "/{contactId}")
    public void updateContact(@PathVariable("contactId") int id,
                              @RequestBody Contact contact) throws NotFoundException {
       contactService.updateContact(id, contact);
    }

    @DeleteMapping(path = "/{contactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("contactId") int id) throws NotFoundException {
        contactService.deleteContact(id);
    }

}
