package com.dins.demo.controllers;

import com.dins.demo.data.UserRepository;
import com.dins.demo.domain.Contact;
import com.dins.demo.domain.User;
import com.dins.demo.exceptions.UserNotFoundException;
import com.dins.demo.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) throws NotFoundException {
        List<User> res = userService.getAllUsers(pageNo, pageSize);
        return new ResponseEntity<List<User>>(res,  HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public User getUserById(@PathVariable("id") int id) throws NotFoundException {
            return userService.getUserById(id);
    }

    @GetMapping(value = "/byname/{name}", produces = "application/json")
    public List<User> getUserByName(@PathVariable(value = "name") String name) throws NotFoundException{
        return userService.getUsersByMatchesName(name);
    }

    @GetMapping(path = "/{id}/all", produces = "application/json")
    public ResponseEntity<List<Contact>> getAllContacts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @PathVariable("id") int id)
            throws NotFoundException {
        List<Contact> res = userService.getAllContacts(pageNo, pageSize, id);
        return new ResponseEntity<List<Contact>>(res,  HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postUser(@RequestBody User user) throws NotFoundException {
         userService.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") int id, @RequestBody User user) throws NotFoundException {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) throws NotFoundException {
        userService.deleteUser(id);
    }
}
