package com.exp.self.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;

    @Version
    private long version;

    public Wallet() {}

    public Wallet(UUID id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public boolean hasSufficientFounds(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
