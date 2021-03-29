package com.dins.demo.controllers;

import com.dins.demo.data.UserRepository;
import com.dins.demo.domain.Contact;
import com.dins.demo.domain.User;
import com.dins.demo.exceptions.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(produces = "application/json")
    public Iterable<User> allUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public User getUser(@PathVariable("id") int id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).orElseThrow(
                    () -> new UserNotFoundException("There is no us3r with id =" + id)
            );
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping(path = "/{id}/all", produces = "application/json")
    public Iterable<Contact> getAllContacts(@PathVariable("id") int id) throws UserNotFoundException {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new UserNotFoundException("There is no us3r with id =" + id)
            );
            return user.getPhoneBook();

        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") int id, @RequestBody User user) {
        try {
            User old = userRepository.findById(id).orElseThrow(
                    () -> new UserNotFoundException("There is no us3r with id =" + id)
            );
            old.setName(user.getName());
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userRepository.deleteById(id);
    }
}
