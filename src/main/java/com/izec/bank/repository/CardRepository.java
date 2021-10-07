package com.izec.bank.repository;

import com.izec.bank.domain.Account;
import com.izec.bank.domain.Card;
import com.izec.bank.dto.CardDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@AllArgsConstructor
@Transactional
public class CardRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public Card findById(long id) {
        return entityManager.find(Card.class, id);
    }

    /**
     * Saved new card in DB
     *
     * @param cardDTO
     * @return
     */
    public CardDTO save(CardDTO cardDTO) {
        Card card = new Card(cardDTO.getBalance(),
                cardDTO.getNumber(),
                cardDTO.getPassword(),
                cardDTO.getCurrencies(),
                entityManager.find(Account.class, cardDTO.getAccountId()));
        entityManager.persist(card);
        return new CardDTO(card.getBalance(),
                card.getNumber(),
                card.getPassword(),
                card.getCurrencies(),
                card.getId());
    }

    /**
     * Returns all users
     *
     * @return
     */
    public List<Card> getAll() {
        return entityManager.createQuery("from Card")
                .getResultList();
    }

    /**
     * Returns user by phone number
     *
     * @param number
     * @return
     */
    public Card findByNumber(long number) {
        return entityManager.createQuery("from Card where number = :number", Card.class)
                .setParameter("number", number)
                .getSingleResult();
    }

    public void update(Card updated) {
        entityManager.merge(updated);
    }
}
