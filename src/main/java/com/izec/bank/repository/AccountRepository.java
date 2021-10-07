package com.izec.bank.repository;

import com.izec.bank.domain.Account;
import com.izec.bank.domain.User;
import com.izec.bank.dto.AccountDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@AllArgsConstructor
@Transactional
public class AccountRepository {

    @PersistenceContext
    private final EntityManager entityManager;


    /**
     * Get account from DB by ID
     *
     * @param id
     * @return
     */
    public Account findById(long id) {
        return entityManager.find(Account.class, id);
    }

    /**
     * The method saves the account to the DB
     *
     * @param accDTO
     * @return
     */
    public AccountDTO save(AccountDTO accDTO) {
        Account account = new Account(accDTO.getBalance(),
                accDTO.getNumber(),
                entityManager.find(User.class, accDTO.getUserId()));
        entityManager.persist(account);
        return new AccountDTO(account.getBalance(),
                account.getNumber(),
                account.getId());
    }

    /**
     * Updated account in BD
     *
     * @param updated
     */
    public AccountDTO update(Account updated, long id) {

        entityManager.merge(updated);
        Account a = entityManager.find(Account.class, id);
        return new AccountDTO(a.getBalance(),
                a.getNumber(),
                a.getId());
    }
}
