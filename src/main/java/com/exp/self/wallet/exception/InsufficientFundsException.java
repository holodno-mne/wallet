package com.exp.self.wallet.exception;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(UUID walletId, long requested, long available) {
        super(String.format(
                "Insufficient funds in wallet %s. Requested: %d, Available: %d",
                walletId, requested, available
        ));
    }
}
