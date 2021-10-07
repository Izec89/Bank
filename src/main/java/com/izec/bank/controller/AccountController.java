package com.izec.bank.controller;

import com.izec.bank.domain.Account;
import com.izec.bank.dto.AccountDTO;
import com.izec.bank.dto.TransferDTO;
import com.izec.bank.dto.TransferDTOTest;
import com.izec.bank.dto.UpdateBalanceDTO;
import com.izec.bank.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/client")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/acc/{id}")
    AccountDTO get(@PathVariable long id) {
        Account acc = accountService.findById(id);

        return new AccountDTO(acc.getBalance(),
                acc.getNumber(),
                acc.getId());
    }

    @PostMapping("/acc/new")
    ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) {

        return new ResponseEntity<>(accountService.addAccount(accountDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    ResponseEntity<AccountDTO> updateBalance(@RequestBody UpdateBalanceDTO update, @PathVariable long id) {

        return new ResponseEntity<>(accountService.updateBalance(update.getBal(), id), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    ResponseEntity<TransferDTOTest> transfer(@RequestBody TransferDTO transferDTO) {
        TransferDTOTest tr = new TransferDTOTest(accountService.transferMoney(transferDTO.getFromId(),
                transferDTO.getToId(), transferDTO.getTransfer()));
        return new ResponseEntity<>(tr, HttpStatus.OK);
    }
}
