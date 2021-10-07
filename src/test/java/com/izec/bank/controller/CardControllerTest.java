package com.izec.bank.controller;

import com.izec.bank.domain.Currency;
import com.izec.bank.dto.CardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.izec.bank.util.AsJson.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CardControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public CardControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void addNewCardTest() throws Exception {
        this.mockMvc.perform(post("/api/card/new")
                        .content(asJsonString(new CardDTO(BigDecimal.valueOf(46781.00),
                                467809874L,
                                "9999",
                                Stream.of(Currency.RUB).collect(Collectors.toSet()),
                                2
                                )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.password").value("9999"));
    }

    @Test
    void getAllCardsTest() throws Exception {
        this.mockMvc.perform(get("/api/card/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"balance\":2843.00,\"number\":99956789,\"password\":\"1234\",\"currencies\":[\"RUB\"],\"account_id\":1},{\"balance\":6787.00,\"number\":42766300,\"password\":\"4343\",\"currencies\":[\"USD\"],\"account_id\":2}]"));
    }
}
