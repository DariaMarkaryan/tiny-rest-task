package com.dins.demo.controllers;
import com.dins.demo.data.UserRepository;
import com.dins.demo.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @GetMapping(produces = "application/json")
    public Iterable<User> allOrders() {
        return userRepository.findAll();
    }
}
