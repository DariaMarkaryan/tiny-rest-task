package com.dins.demo.services;

import com.dins.demo.entites.Contact;
import com.dins.demo.entites.User;
import com.dins.demo.repos.ContactRepository;
import com.dins.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;


    public Contact getContact(int id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.orElse(null);
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public ResponseEntity<Contact> updateContact(int contactId, Contact newContactInfo) {
        Optional<Contact> oldContact = contactRepository.findById(contactId);
        if (oldContact.isEmpty()) {
            return (ResponseEntity<Contact>) ResponseEntity.notFound();
        } else {
            Contact old = oldContact.get();
            old.setUser(userRepository.findById(newContactInfo.getId()).get());
            old.setPhone(newContactInfo.getPhone());
            old.setContactname(newContactInfo.getContactname());
            return ResponseEntity.ok().body(contactRepository.save(old));
        }
    }

    public void deleteContact(int id) {
        if (contactRepository.findById(id).isPresent())
            contactRepository.deleteById(id);
    }

    public Page<Contact> findAllByUser(User user, Pageable pageable) {
        return contactRepository.findAllByUser(user, pageable);
    }
}
