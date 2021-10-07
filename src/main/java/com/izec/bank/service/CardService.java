package com.izec.bank.service;

import com.izec.bank.domain.Card;
import com.izec.bank.dto.CardDTO;
import com.izec.bank.repository.CardRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card findById(long id) {
        Optional<Card> card = Optional.ofNullable(cardRepository.findById(id));
        if (card.isPresent()) {
            return card.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public CardDTO addCard(CardDTO cardDTO) {
//        if (cardRepository.findByNumber(cardDTO.getNumber()) != null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Карта уже существует");
//        }
        try {
            return cardRepository.save(cardDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Card findByNumber(long number) {
        Optional<Card> card = Optional.ofNullable(cardRepository.findByNumber(number));
        if (card.isPresent()) {
            return card.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Извините, карта не найдена");
        }
    }

    public List<Card> findAll() {
        return cardRepository.getAll();
    }

    public void updateBalance(BigDecimal residue, long id) {
        Optional<Card> optionalCard = Optional.ofNullable(cardRepository.findById(id));
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setBalance(card.getBalance().add(residue));
            cardRepository.update(card);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void transferMoney(long fromId, long toId, BigDecimal money) {
        Optional<Card> from = Optional.ofNullable(cardRepository.findById(fromId));
        Optional<Card> to = Optional.ofNullable(cardRepository.findById(toId));

        if (from.equals(Optional.empty()) || to.equals(Optional.empty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (from.get().getBalance().compareTo(money) < 0) {  //TODO
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        updateBalance(BigDecimal.ZERO.subtract(money), fromId);
        updateBalance(money, toId);

    }
}
