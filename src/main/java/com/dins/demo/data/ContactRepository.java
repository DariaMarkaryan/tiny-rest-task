package com.dins.demo.data;

import com.dins.demo.domain.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository
        extends CrudRepository<Contact, Long> {
}
