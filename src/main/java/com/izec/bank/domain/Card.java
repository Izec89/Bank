package com.izec.bank.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;

    @Column(unique = true)
    private Long number;
    private String password;


    @ElementCollection(targetClass = Currency.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "card_currencies", joinColumns = @JoinColumn(name = "card_id"))
    @Enumerated(EnumType.STRING)
    private Set<Currency> currencies;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Card(BigDecimal balance, Long number, String password, Set<Currency> currencies, Account account) {
        this.balance = balance;
        this.number = number;
        this.password = password;
        this.currencies = currencies;
        this.account = account;
    }
}
