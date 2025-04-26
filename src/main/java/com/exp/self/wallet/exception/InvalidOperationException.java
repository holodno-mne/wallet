package com.exp.self.wallet.exception;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String operationType) {
        super("Invalid operation type: " + operationType + ". Use DEPOSIT or WITHDRAW");
    }
}
