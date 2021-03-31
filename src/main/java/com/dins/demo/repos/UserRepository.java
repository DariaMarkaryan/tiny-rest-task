package com.dins.demo.repos;

import com.dins.demo.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository
        extends PagingAndSortingRepository<User, Integer> {
    Page<User> findByNameContains(String name, Pageable pageable);

    Page<User> findAll(Pageable pageable);
}
