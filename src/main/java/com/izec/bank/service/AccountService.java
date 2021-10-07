package com.izec.bank.service;

import com.izec.bank.domain.Account;
import com.izec.bank.domain.User;
import com.izec.bank.dto.AccountDTO;
import com.izec.bank.dto.UserDTO;
import com.izec.bank.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account findById(long id) {
        Optional<Account> account = Optional.ofNullable(accountRepository.findById(id));
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public AccountDTO addAccount(AccountDTO accountDTO) {
        try {
            return accountRepository.save(accountDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public AccountDTO updateBalance(BigDecimal residue, long id) {
        Optional<Account> acc = Optional.ofNullable(accountRepository.findById(id));
        if (acc.isPresent()) {
            Account account = acc.get();
            account.setBalance(account.getBalance().add(residue));
            return accountRepository.update(account, id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public String transferMoney(long fromId, long toId, BigDecimal money) {
        Optional<Account> from = Optional.ofNullable(accountRepository.findById(fromId));
        Optional<Account> to = Optional.ofNullable(accountRepository.findById(toId));

        if (from.equals(Optional.empty()) || to.equals(Optional.empty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (from.get().getBalance().compareTo(money) < 0) {  //TODO
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        updateBalance(BigDecimal.ZERO.subtract(money), fromId);
        updateBalance(money, toId);

        return accountRepository.findById(fromId).getBalance() + "" + accountRepository.findById(toId).getBalance();

    }

}
