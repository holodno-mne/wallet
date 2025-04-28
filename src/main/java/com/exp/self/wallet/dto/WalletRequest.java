package com.exp.self.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;


public class WalletRequest {
    private UUID walletId;
    private OperationType type;
    private BigDecimal amount;

    public UUID getWalletId() {
        return walletId;
    }

    public OperationType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public WalletRequest(UUID walletId, OperationType type, BigDecimal amount) {
        this.walletId = walletId;
        this.type = type;
        this.amount = amount;
    }
}
