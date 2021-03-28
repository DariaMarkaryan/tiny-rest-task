package com.dins.demo.data;

import com.dins.demo.domain.PhoneBook;
import org.springframework.data.repository.CrudRepository;

public interface PhoneBookRepository
        extends CrudRepository<PhoneBook, Long> {
}
