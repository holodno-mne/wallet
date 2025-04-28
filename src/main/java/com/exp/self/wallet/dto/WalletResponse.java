package com.exp.self.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;


public class WalletResponse {
    public UUID walletId;
    public BigDecimal balance;

    public WalletResponse(UUID walletId, BigDecimal balance) {
        this.walletId = walletId;
        this.balance = balance;
    }
}
