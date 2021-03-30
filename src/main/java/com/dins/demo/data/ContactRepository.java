package com.dins.demo.data;

import com.dins.demo.domain.Contact;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactRepository
        extends PagingAndSortingRepository<Contact, Integer> {
}
