package com.dins.demo;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TelBookApplicationTests {

//    private final int port = 8080;
//
//    @Test
//    public void getUser() throws Exception {
//        Traverson traverson =
//                new Traverson(new URI("http://localhost:" + this.port + "/user/1"), MediaTypes.HAL_JSON);
//        String name = traverson.follow("self").toObject("$.name");
//        Assertions.assertEquals(name, "Dasha");
//    }

}
