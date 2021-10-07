package com.izec.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.izec.bank.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private BigDecimal balance;
    private Long number;
    private String password;
    private Set<Currency> currencies;
    @JsonProperty("account_id")
    private long accountId;
}
