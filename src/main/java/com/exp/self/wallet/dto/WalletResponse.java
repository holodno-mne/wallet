package com.exp.self.wallet.dto;

import lombok.Data;

import java.util.UUID;


public class WalletResponse {
    public UUID walletId;
    public long balance;

    public WalletResponse(UUID walletId, long balance) {
        this.walletId = walletId;
        this.balance = balance;
    }
}
