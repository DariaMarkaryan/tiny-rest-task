package com.dins.demo.services;

import com.dins.demo.data.UserRepository;
import com.dins.demo.domain.Contact;
import com.dins.demo.domain.User;
import com.dins.demo.exceptions.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(Integer pgNo, Integer pgSz) throws NotFoundException {
        Pageable paging = PageRequest.of(pgNo, pgSz);
        Page<User> pageRes = userRepository.findAll(paging);
        if (pageRes.hasContent()) {
            return pageRes.getContent();
        } else {
            throw new UserNotFoundException("There is empty");
        }
    }

    public User getUserById(int id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("There is no user with id = " + id);
        }
    }

    public List<User> getUsersByMatchesName(String name) throws UserNotFoundException {
        List<User> res = userRepository.findByNameContains(name);
        if(res != null){
            return res;
        } else {
           throw new UserNotFoundException("There's no suitable user's name");
        }
    }

    public void createUser(User user) throws NotFoundException {
        userRepository.save(user);
    }

    public void updateUser(int id, User user) throws NotFoundException {
        Optional<User> foundUser = userRepository.findById(id);
        foundUser.ifPresent(value -> value.setName(user.getName()));
    }

    public void deleteUser(int userId) throws NotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException("There is already no users like that");
        }
    }

    public List<Contact> getAllContacts(int pageNo, int pageSize, int id) throws UserNotFoundException {
        Optional<User> usr = userRepository.findById(id);
        if (usr.isPresent()) {
            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Contact> contactsPage = new PageImpl<>(usr.get().getPhoneBook());
            if (contactsPage.hasContent()) {
                return contactsPage.getContent();
            } else {
                return new ArrayList<Contact>();
            }
        } else {
            throw new UserNotFoundException("There is no user with id = " + id);
        }
    }
}
