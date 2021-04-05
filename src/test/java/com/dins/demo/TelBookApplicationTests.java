package com.dins.demo;

import com.dins.demo.assemblers.UserAssembler;
import com.dins.demo.entites.User;
import com.dins.demo.entites.UserModel;
import com.dins.demo.repos.UserRepository;
import com.dins.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TelBookApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService userService;
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
        User user = createTestUser("qwerty");
        mockMvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("qwerty"));
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
        Pageable pageable =  PageRequest.of(0, 10);
        Page<User> page = repository.findAll(pageable);
        PagedModel<UserModel> res = pagedResourcesAssembler.toModel(page, userAssembler);
        ResponseEntity<PagedModel<UserModel>> pm = new ResponseEntity<>(res, HttpStatus.OK);
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writeValueAsString(res)))
                .andDo(print());
//      the pm returns in json, but needed in HAL+JSON!!!
    }

    @Test
    public void deleteUserById() {

    }

    @Test
    public void getUserPhonebook() {

    }

    @Test
    public void addContactByTd() {
    }

    @Test
    public void alterContactByTd() {

    }

    @Test
    public void deleteContactByTd() {
    }

    @Test
    public void getContactByTd() {

    }

    private User createTestUser(String name) {
        User user = new User();
        user.setName(name);
        return repository.save(user);
    }
}
