package com.exp.self.wallet.dto;

import java.time.Instant;

public class ErrorResponse {
    private String message;
    private Instant timestamp = Instant.now();

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
