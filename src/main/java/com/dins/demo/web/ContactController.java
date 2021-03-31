package com.dins.demo.web;

import com.dins.demo.entites.Contact;
import com.dins.demo.services.ContactService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping(path = "/{contactId}", produces = "application/json")
    public ResponseEntity<Contact> getContactById(@PathVariable("contactId") int id) {
        Contact contact = contactService.getContact(id);
        if(contact != null){
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postContact(@RequestBody Contact contact) {
        contactService.createContact(contact);
    }

    @PutMapping(path = "/{contactId}")
    public void updateContact(@PathVariable("contactId") int id,
                              @RequestBody Contact contact) {
        if (contactService.getContact(id) != null)
            contactService.updateContact(id, contact);
    }

    @DeleteMapping(path = "/{contactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("contactId") int id) {
        contactService.deleteContact(id);
    }

}
