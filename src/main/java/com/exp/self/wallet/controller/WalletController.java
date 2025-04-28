package com.exp.self.wallet.controller;

import com.exp.self.wallet.dto.WalletRequest;
import com.exp.self.wallet.dto.WalletResponse;
import com.exp.self.wallet.entity.Wallet;
import com.exp.self.wallet.exception.InvalidOperationException;
import com.exp.self.wallet.repository.WalletRepository;
import com.exp.self.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<WalletResponse> processOperation(@RequestBody WalletRequest request) {

        Wallet wallet = switch (request.getType()) {
            case DEPOSIT -> walletService.deposit(request.getWalletId(), request.getAmount());
            case WITHDRAW -> walletService.withdraw(request.getWalletId(), request.getAmount());
            default -> throw new InvalidOperationException(request.getType());
        };

        return ResponseEntity.ok(new WalletResponse(request.getWalletId(), wallet.getBalance()));
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getBalance(
            @PathVariable UUID walletId) {

        BigDecimal balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(
                new WalletResponse(walletId, balance)
        );


    }
}
