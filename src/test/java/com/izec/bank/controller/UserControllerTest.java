package com.izec.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izec.bank.domain.Role;
import com.izec.bank.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.izec.bank.util.AsJson.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    public UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void getAllUsersTest() throws Exception {
        this.mockMvc.perform(get("/api/client/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"active\":true,\"email\":\"some@mail.ru\",\"activationCode\":\"3456\",\"phoneNumber\":\"89255431017\",\"roles\":[\"USER\"],\"firstname\":\"Alex\",\"lastname\":\"Echo\"},{\"active\":true,\"email\":\"exclusive@mail.ru\",\"activationCode\":\"4856\",\"phoneNumber\":\"89201899878\",\"roles\":[\"USER\"],\"firstname\":\"Inna\",\"lastname\":\"Guk\"}]"));
    }

    @Test
    void addNewUserTest() throws Exception {
        this.mockMvc.perform(post("/api/client/new-client")
                        .content(asJsonString(new UserDTO("firstname", "lastname",
                                true, "sometest@mail.ru", "some_code",
                                "89038836950", Stream.of(Role.USER).collect(Collectors.toSet()))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.phoneNumber").value("89038836950"));
    }

    @Test
    void findUserByIdTest() throws Exception {
        this.mockMvc.perform(get("/api/client/user-id/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("exclusive@mail.ru"));
    }


}
