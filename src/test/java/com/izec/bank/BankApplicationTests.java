package com.izec.bank;


import com.izec.bank.controller.AccountController;
import com.izec.bank.controller.CardController;
import com.izec.bank.controller.UserController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BankApplicationTests {

    private final MockMvc mockMvc;

    private final UserController userController;
    private final AccountController accountController;
    private final CardController cardController;

    @Autowired
    public BankApplicationTests(MockMvc mockMvc, UserController userController, AccountController accountController, CardController cardController) {
        this.mockMvc = mockMvc;
        this.userController = userController;
        this.accountController = accountController;
        this.cardController = cardController;
    }

    @Test
    void loadContext() {
        assertThat(userController).isNotNull();
        assertThat(accountController).isNotNull();
        assertThat(cardController).isNotNull();
    }


}