package com.dins.demo.services;

import com.dins.demo.entites.Contact;
import com.dins.demo.entites.User;
import com.dins.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        Page<User> pageRes = userRepository.findAll(pageable);
        if (pageRes.hasContent()) {
            return pageRes;
        } else {
            return null;
        }
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public Page<User> getUsersByMatchesName(String name, Pageable pageable) {
        Page<User> res = userRepository.findByNameContains(name, pageable);
        if (res.hasContent()) {
            return res;
        } else {
            return null;
        }
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(int id, User user) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent())
            foundUser.ifPresent(value -> value.setName(user.getName()));
    }

    public void deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent())
            userRepository.deleteById(userId);
    }

    public Page<Contact> getAllContacts(User usr) {
        Page<Contact> contactsPage = new PageImpl<>(usr.getPhoneBook());
        if (contactsPage.hasContent()) {
            return contactsPage;
        } else {
            return null;
        }
    }
}
