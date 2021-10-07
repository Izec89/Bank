package com.izec.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferDTO {
    @JsonProperty("fromId")
    private long fromId;

    @JsonProperty("toId")
    private long toId;
    private BigDecimal transfer;
}
