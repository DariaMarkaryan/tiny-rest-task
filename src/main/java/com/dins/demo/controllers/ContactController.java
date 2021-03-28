package com.dins.demo.controllers;

import com.dins.demo.data.ContactRepository;
import com.dins.demo.domain.Contact;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {
private ContactRepository contactRepository;

    @GetMapping(produces = "application/json")
    public Iterable<Contact> allPhonebooks() {
        return contactRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postContact(@RequestBody Contact contact) { contactRepository.save(contact); }

    @PutMapping(path="/{contactId}")
    public void updateContact(@PathVariable("contactId") int id,
                              @RequestBody Contact contact) throws NotFoundException {
        Contact old = contactRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("couldn't find contact with id = " + contact.getId()));
        old.setName(contact.getName());
        old.setPhoneNumber(contact.getPhoneNumber());
     }

    @DeleteMapping(path="/{contactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("contactId") int id){
        contactRepository.deleteById(id);
    }

    @GetMapping(path="/{contactId}", produces = "application/json")
    public Contact getContact(@PathVariable("contactId") int id) throws NotFoundException {
        return contactRepository.findById(id).orElseThrow(
                () -> new NotFoundException("couldn't find contact with id = " + id)
        );

    }

}
