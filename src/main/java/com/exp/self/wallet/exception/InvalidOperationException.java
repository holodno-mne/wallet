package com.exp.self.wallet.exception;

import com.exp.self.wallet.dto.OperationType;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(OperationType operationType) {
        super("Invalid operation type: " + operationType + ". Use DEPOSIT or WITHDRAW");
    }
}
