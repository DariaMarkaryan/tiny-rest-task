package com.dins.demo.web;


import com.dins.demo.entites.Contact;
import com.dins.demo.entites.User;
import com.dins.demo.exceptions.UserNotFoundException;
import com.dins.demo.repos.UserRepository;
import com.dins.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RepositoryRestController
@RequestMapping(path = "/users", produces = "application/hal+json")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${spring.data.rest.default-page-size}")
    private int pageSize;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @PageableDefault Pageable pageable) {
        Page<User> page = userService.getAllUsers(pageable);
        if (page != null) {
            for (User u : page.getContent()) {
                u.add(linkTo(methodOn(UserController.class)
                        .getUserById(u.getId(), pageable))
                        .withSelfRel());
                u.add(linkTo(
                        methodOn(UserController.class)
                                .getAllContacts(u.getId(), pageable))
                        .withRel("phonebook"));
            }
            return new ResponseEntity<>(page, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id,
                                            @PageableDefault Pageable pageable) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.add(linkTo(methodOn(UserController.class).
                    getAllContacts(id, pageable)).
                    withRel("contacts"));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/search-by-name/{name}")
    public ResponseEntity<Page<User>> getUsersByName(@PathVariable(value = "name") String name,
                                                     @PageableDefault Pageable pageable) {
        Page<User> page = userService.getUsersByMatchesName(name, pageable);

        if (page != null) {
            for (User u : page.getContent()) {
                u.add(linkTo(methodOn(UserController.class)
                        .getUserById(u.getId(), pageable))
                        .withSelfRel());
                u.add(linkTo(
                        methodOn(UserController.class)
                                .getAllContacts(u.getId(), pageable))
                        .withRel("phonebook"));
            }
            return new ResponseEntity<>(page, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}/phonebook")
    public ResponseEntity<Page<Contact>> getAllContacts(@PathVariable("id") int id,
                                                        @PageableDefault Pageable pageable) {
        User user = userService.getUserById(id);
        if (user != null) {
            Page<Contact> page = userService.getAllContacts(user);
            if (page != null) {
                return new ResponseEntity<>(page, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> postUser(@RequestBody User user) {
        User saved = userService.createUser(user);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id,
                                           @RequestBody @Valid User user) throws UserNotFoundException {
        return userService.updateUser(id, user);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteUser(@PathVariable("id") int id) {
        return userService.deleteUser(id);
    }
}