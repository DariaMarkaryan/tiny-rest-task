package com.dins.demo.data;

import com.dins.demo.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository
        extends CrudRepository<User, Integer> {
}
