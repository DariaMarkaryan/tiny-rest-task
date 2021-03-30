package com.dins.demo.services;

import com.dins.demo.data.ContactRepository;
import com.dins.demo.domain.Contact;
import com.dins.demo.domain.User;
import com.dins.demo.exceptions.ContactNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact getContact(int id) throws NotFoundException {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            return contact.get();
        } else {
            throw new ContactNotFoundException("There is no contact with id = " + id);
        }
    }

    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }

    public void updateContact(int contactId, Contact newContactInfo) throws NotFoundException {
        Optional<Contact> oldContact = contactRepository.findById(contactId);
        if (oldContact.isPresent()) {
            oldContact.get().setContactname(newContactInfo.getContactname());
            oldContact.get().setPhone(newContactInfo.getPhone());
        } else {
            throw new ContactNotFoundException("There is already no users like that");
        }
    }

    public void deleteContact(int id) throws NotFoundException {
        if (contactRepository.findById(id).isPresent()) {
            contactRepository.deleteById(id);
        } else {
            throw new ContactNotFoundException("There is already no contacts with id = " + id);
        }
    }

}
