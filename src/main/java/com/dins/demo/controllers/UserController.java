package com.dins.demo.controllers;
import com.dins.demo.data.PhoneBookRepository;
import com.dins.demo.data.UserRepository;
import com.dins.demo.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    private PhoneBookRepository phoneBookRepository;

}
