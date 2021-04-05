package com.dins.demo;

import com.dins.demo.entites.User;
import com.dins.demo.repos.UserRepository;
import com.dins.demo.services.ContactService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//поднимает весь контекст
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ContactService contactService;

    final private static String baseUrl = "http://localhost:";

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void createUser() {
        User user = new User();
        user.setName("Dasha");
        ResponseEntity<User> response = restTemplate.postForEntity(baseUrl + port + "/user", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getName(), is("Dasha"));

    }

    @Test
    public void getUserById() {
        int id = createTestUser("Masha").getId();
        System.out.println(id);
        User response = restTemplate.getForObject(baseUrl + port + "/user/{id}", User.class, id);
        assertThat(response.getName(), is("Masha"));
    }

    @Test
    public void getByNamePart() {
//        createTestUser("Masha");
//        createTestUser("Dima");
        ResponseEntity<List<User>> response = restTemplate.exchange(baseUrl + port + "/user/byname/ash", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        List<User> users = response.getBody();
        assertThat(users, hasSize(1));
    }

    @Test
    public void getAllUsers() {

    }

    @Test
    public void deleteUserById() {

    }

    @Test
    public void alterUser() {

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

