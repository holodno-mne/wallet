package com.exp.self.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "BALANCE", nullable = false)
    private long balance;

    @Version
    private long version;

    public Wallet() {}

    public Wallet(UUID id, long balance) {
        this.id = id;
        this.balance = balance;
    }

    public boolean hasSufficientFounds(long amount) {
        return balance >= amount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
