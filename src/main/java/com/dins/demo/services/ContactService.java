package com.dins.demo.services;

import com.dins.demo.entites.Contact;
import com.dins.demo.repos.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact getContact(int id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.orElse(null);
    }

    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }

    public void updateContact(int contactId, Contact newContactInfo) {
        Optional<Contact> oldContact = contactRepository.findById(contactId);
        if (oldContact.isPresent()) {
            oldContact.get().setContactname(newContactInfo.getContactname());
            oldContact.get().setPhone(newContactInfo.getPhone());
        }
    }

    public void deleteContact(int id) {
        if (contactRepository.findById(id).isPresent())
            contactRepository.deleteById(id);
    }
}
