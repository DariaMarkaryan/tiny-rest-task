package com.dins.demo;

import com.dins.demo.entites.ContactModel;
import com.dins.demo.entites.User;
import com.dins.demo.entites.UserModel;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
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
    public void testGetUserById() {
        User user = restTemplate.getForObject(getRootUrl() + "/user/2", User.class);
        System.out.println(user.getName());
        Assertions.assertEquals(user.getName(), "Masha");
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
        user.setName("Grigory");
        user.setId(1);
        ResponseEntity<UserModel> postResponse = restTemplate.postForEntity(getRootUrl() + "/user", user, UserModel.class);
        assertNotNull(postResponse);
    }

    @Test
    public void testUpdateUser() {
        int id = 2;
        UserModel user = restTemplate.getForObject(getRootUrl() + "/user/" + id, UserModel.class);
        user.setName("Maria");
        restTemplate.put(getRootUrl() + "/user/" + id, user);

        UserModel updatedUser = restTemplate.getForObject(getRootUrl() + "/user/" + id, UserModel.class);
        assertNotNull(updatedUser);
    }

    @Test
    public void testDeleteUser() throws Exception {
        int id = 3;//Удостоверьтесь, что вы ещё не удаили эту запись
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

