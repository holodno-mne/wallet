package com.exp.self.wallet.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(UUID walletId, BigDecimal requested, BigDecimal available) {
        super(String.format(
                "Insufficient funds in wallet %s. Requested: %d, Available: %d",
                walletId, requested, available
        ));
    }
}
