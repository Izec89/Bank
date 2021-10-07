package com.izec.bank.controller;

import com.izec.bank.domain.Card;
import com.izec.bank.domain.User;
import com.izec.bank.dto.CardDTO;
import com.izec.bank.dto.TransferDTO;
import com.izec.bank.dto.UpdateBalanceDTO;
import com.izec.bank.dto.UserDTO;
import com.izec.bank.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private final CardService cardService;

    @GetMapping("/get/{id}")
    CardDTO getById(@PathVariable long id) {
        Card card = cardService.findById(id);
        return new CardDTO(card.getBalance(),
                card.getNumber(),
                card.getPassword(),
                card.getCurrencies(),
                card.getAccount().getId());
    }

    @GetMapping("/{number}")
    CardDTO get(@PathVariable long number) {
        Card card = cardService.findByNumber(number);
        return new CardDTO(card.getBalance(),
                card.getNumber(),
                card.getPassword(),
                card.getCurrencies(),
                card.getAccount().getId());
    }

    @GetMapping("/all")
    List<CardDTO> findAll() {
        List<Card> list = cardService.findAll();
        List<CardDTO> result = new ArrayList<>();
        list.forEach(o -> {
            result.add(new CardDTO(o.getBalance(),
                    o.getNumber(),
                    o.getPassword(),
                    o.getCurrencies(),
                    o.getAccount().getId()));
        });

        return result;
    }

    @PostMapping("/new")
    ResponseEntity<CardDTO> addCard(@RequestBody CardDTO cardDTO) {
        return new ResponseEntity<>(cardService.addCard(cardDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    void updateBalance(@RequestBody UpdateBalanceDTO update, @PathVariable long id) {
        cardService.updateBalance(update.getBal(), id);
    }

    @PostMapping("/transfer")
    void transfer(@RequestBody TransferDTO transferDTO) {
        cardService.transferMoney(transferDTO.getFromId(), transferDTO.getToId(), transferDTO.getTransfer());
    }
}
