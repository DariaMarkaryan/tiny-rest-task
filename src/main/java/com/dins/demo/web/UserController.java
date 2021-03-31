package com.dins.demo.web;

import com.dins.demo.assemblers.ContactAssembler;
import com.dins.demo.assemblers.UserAssembler;
import com.dins.demo.entites.Contact;
import com.dins.demo.entites.ContactModel;
import com.dins.demo.entites.User;
import com.dins.demo.entites.UserModel;
import com.dins.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RepositoryRestController
@RequestMapping(path = "/user", produces = "application/hal+json")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${spring.data.rest.default-page-size}")
    private int pageSize;

    @Autowired
    private UserAssembler userModelAssembler;

    @Autowired
    private ContactAssembler contactAssembler;

    @Autowired
    private PagedResourcesAssembler<User> pagedResourcesAssembler;

    @Autowired
    private PagedResourcesAssembler<Contact> contactPagedResourcesAssembler;

    @GetMapping("/all")
    public ResponseEntity<PagedModel<UserModel>> getAllUsers(Pageable pageable) {
        Page<User> page = userService.getAllUsers(pageable);
        if (page != null) {
            PagedModel<UserModel> res = pagedResourcesAssembler
                    .toModel(page, userModelAssembler);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.add(linkTo(methodOn(UserController.class).
                    getAllContacts(id)).
                    withRel("contacts"));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/byname/{name}")
    public ResponseEntity<PagedModel<UserModel>> getUsersByName(@PathVariable(value = "name") String name,
                                                                @PageableDefault Pageable pageable) {
        Page<User> page = userService.getUsersByMatchesName(name, pageable);

        if (page != null) {
            PagedModel<UserModel> res = pagedResourcesAssembler
                    .toModel(page, userModelAssembler);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}/phonebook")
    public ResponseEntity<PagedModel<ContactModel>> getAllContacts(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            Page<Contact> page = userService.getAllContacts(user);
            if (page != null) {
                PagedModel<ContactModel> res = contactPagedResourcesAssembler
                        .toModel(page, contactAssembler);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") int id,
                           @RequestBody User user) {
        if (userService.getUserById(id) != null)
            userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user != null)
            userService.deleteUser(id);
    }
}