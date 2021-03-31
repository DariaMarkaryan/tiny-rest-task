package com.dins.demo;

import com.dins.demo.entites.ContactModel;
import com.dins.demo.entites.User;
import com.dins.demo.entites.UserModel;
import com.dins.demo.repos.UserRepository;
import com.dins.demo.services.UserService;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @MockBean
    private UserRepository userRepository;

    @Before
    public void init() {
        User user = new User();
        user.setName("Ekaterina");
    when(userRepository.findById(1)).thenReturn(Optional.of(user));
    }

    @Test
    public void testGetAllUsers() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/user/all",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById() throws JSONException {
//        User user = restTemplate.getForObject(getRootUrl() + "/user/2", User.class);
//        System.out.println(user.getName());
//        Assertions.assertEquals(user.getName(), "Masha");
    String expected ="{name:\"Ekaterina\"}";
        ResponseEntity<String> response = restTemplate.getForEntity("/user/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testGetUsersByName() {
        UserModel user = restTemplate.getForObject(getRootUrl() + "/user/byname/asha", UserModel.class);
        System.out.println(user.getName());
        assertNotNull(user);
    }

    @Test
    public void testAddUser() {
        UserModel user = new UserModel();
        int then = userService.getAllUsers(Pageable.unpaged()).getSize();
        user.setName("Grigory");
        ResponseEntity<UserModel> postResponse = restTemplate.postForEntity(getRootUrl() + "/user", user, UserModel.class);
        int now = userService.getAllUsers(Pageable.unpaged()).getSize();
        assertEquals(then, now - 1);
    }

    @Test
    public void testUpdateUser() {
        int id = 2;
        User user = restTemplate.getForObject(getRootUrl() + "/user/" + id, User.class);
        user.setName("Maria");
        restTemplate.put(getRootUrl() + "/user/" + id, user);

        UserModel updatedUser = restTemplate.getForObject(getRootUrl() + "/user/" + id, UserModel.class);
        assertNotNull(updatedUser);
    }

    @Test
    public void testDeleteUser() throws Exception {
        int id = 33;//Удостоверьтесь, что вы ещё не удаили эту запись
        UserModel user = restTemplate.getForObject(getRootUrl() + "/user/" + id, UserModel.class);
        assertNotNull(user);
        restTemplate.delete(getRootUrl() + "/user/" + id);
        try {
            user = restTemplate.getForObject(getRootUrl() + "/user/" + id, UserModel.class);
        } catch (final HttpClientErrorException e) {
            Assertions.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void getPhonebook() {
        int id = 2;
        ContactModel phonebook = restTemplate.getForObject(getRootUrl() + "/user/" + id + "/phonebook", ContactModel.class);
        assertNotNull(phonebook);
    }
}

