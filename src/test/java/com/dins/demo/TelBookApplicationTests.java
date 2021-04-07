package com.dins.demo;

import com.dins.demo.assemblers.UserAssembler;
import com.dins.demo.entites.Contact;
import com.dins.demo.entites.ContactModel;
import com.dins.demo.entites.User;
import com.dins.demo.entites.UserModel;
import com.dins.demo.repos.UserRepository;
import com.dins.demo.services.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TelBookApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository repository;
    @Autowired
    private ContactService contactService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAssembler userAssembler;
    @Autowired
    private PagedResourcesAssembler<User> pagedResourcesAssembler;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void addUser() throws Exception {
        UserModel user = new UserModel();
        user.setName("qwerty");
        mockMvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("qwerty"));
    }

    @Test
    public void getUserById() throws Exception {
        int id = createTestUser("tatiana").getId();
        System.out.println(id);
        mockMvc.perform(
                get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("tatiana"));
    }

    @Test
    public void updateUser() throws Exception {
        int id = createTestUser("katerina").getId();
        System.out.println(id);
        mockMvc.perform(
                get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("katerina"));
        User toUpd = new User();
        toUpd.setName("Nick");
        toUpd.setId(id);
        mockMvc.perform(
                put("/user/{id}", id)
                        .contentType("application/json")
                        .content("{\"name\":\"Nick\"}"));
        mockMvc.perform(
                get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nick"));
    }

    @Test
    public void getAllUsers() throws Exception {
        User u1 = createTestUser("Tatiana");
        User u2 = createTestUser("Svetlana");
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = repository.findAll(pageable);
        PagedModel<UserModel> res = pagedResourcesAssembler.toModel(page, userAssembler);
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Tatiana"))
                .andExpect(jsonPath("$.content[1].name").value("Svetlana"))
                .andDo(print());
    }

    @Test
    public void deleteUserById() throws Exception {
        int id = createTestUser("Samuel").getId();
        Assertions.assertEquals(1, StreamSupport.stream(repository.findAll().spliterator(), false).count());
        mockMvc.perform(delete("/user/{id}", id))
                .andExpect(status().isOk());
        Assertions.assertEquals(0, StreamSupport.stream(repository.findAll().spliterator(), false).count());
    }

    @Test
    public void getPhonebook() throws Exception {
        User user = createTestUser("Igor");
        int id = user.getId();
        Contact contact = createTestContact(user, "TTT", "2389745");
        mockMvc.perform(get("/user/{id}/phonebook", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].contactname").value("TTT"));
    }

    @Test
    public void add_and_alter_andDeleteContact() throws Exception {
        User user = createTestUser("Irma");
        int user_id = user.getId();
        ContactModel contact = new ContactModel();
        contact.setContactname("Galina");
        contact.setPhone("89992442616");
        contact.setUser_id(user_id);
        //add
        mockMvc.perform(post("/contact").
                content(objectMapper.writeValueAsString(contact))
                .contentType("application/json"))
                .andExpect(jsonPath("$.contactname").value("Galina"));
        //alter
        List<Contact> list = contactService.findAllByUser(user);
        Assert.assertEquals(1, list.size());
        int contact_id = list.get(0).getId();
        ContactModel updated = new ContactModel();
        updated.setContactname("Annka");
        updated.setPhone(contact.getPhone());
        updated.setUser_id(user_id);
        mockMvc.perform(
                put("/contact/{id}", contact_id)
                        .content(objectMapper.writeValueAsString(updated))
                        .contentType("application/json"));
        mockMvc.perform(
                get("/contact/{id}", contact_id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactname").value("Annka"));
        //delete
        mockMvc.perform(
                delete("/contact/{id}", contact_id))
                .andExpect(status().isNoContent());
        list = contactService.findAllByUser(user);
        Assert.assertEquals(0, list.size());
    }

    private User createTestUser(String name) {
        User user = new User();
        user.setName(name);
        return repository.save(user);
    }

    private Contact createTestContact(User user, String name, String phone) {
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setPhone(phone);
        contact.setContactname(name);
        return contactService.createContact(contact);
    }
}
