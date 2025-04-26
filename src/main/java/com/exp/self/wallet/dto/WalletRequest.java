package com.exp.self.wallet.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WalletRequest {
    private UUID walletId;
    private OperationType type;
    private long amount;

    public UUID getWalletId() {
        return walletId;
    }

    public OperationType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public WalletRequest(UUID walletId, OperationType type, long amount) {
        this.walletId = walletId;
        this.type = type;
        this.amount = amount;
    }
}
