package com.izec.bank.controller;

import com.izec.bank.dto.AccountDTO;
import com.izec.bank.dto.TransferDTO;
import com.izec.bank.dto.UpdateBalanceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.izec.bank.util.AsJson.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public AccountControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void addNewAccountTest() throws Exception {
        this.mockMvc.perform(post("/api/client/acc/new")
                        .content(asJsonString(new AccountDTO(BigDecimal.valueOf(45690.99),
                                876787L, 2)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.number").value("876787"));
    }

    @Test
    void updateBalanceAccountTest() throws Exception {
        this.mockMvc.perform(patch("/api/client/update/{id}", 2)
                        .content(asJsonString(new UpdateBalanceDTO(BigDecimal.valueOf(1.00))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("6788.0"));
    }

    @Test
    void transferMoneyAccountTest() throws Exception {
        this.mockMvc.perform(post("/api/client/transfer")
                .content(asJsonString(new TransferDTO(1,
                        2, BigDecimal.valueOf(100))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balanceString").value("2743.006887.00"));
    }

}
