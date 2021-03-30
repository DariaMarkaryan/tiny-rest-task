package com.dins.demo.data;

import com.dins.demo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository
        extends PagingAndSortingRepository<User, Integer> {
    List<User> findByNameContains(String name);
}
