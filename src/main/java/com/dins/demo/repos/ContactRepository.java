package com.dins.demo.repos;

import com.dins.demo.entites.Contact;
import com.dins.demo.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ContactRepository
        extends PagingAndSortingRepository<Contact, Integer> {
    Page<Contact> findAll(Pageable pageable);
}
