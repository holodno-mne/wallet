package com.exp.self.wallet.controller;

import com.exp.self.wallet.dto.WalletRequest;
import com.exp.self.wallet.dto.WalletResponse;
import com.exp.self.wallet.exception.InvalidOperationException;
import com.exp.self.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

import static com.exp.self.wallet.dto.OperationType.DEPOSIT;
import static com.exp.self.wallet.dto.OperationType.WITHDRAW;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<WalletResponse> processOperation(@RequestBody WalletRequest request) {

        switch (request.getType()) {
            case DEPOSIT -> walletService.deposit(request.getWalletId(), request.getAmount());
            case WITHDRAW -> walletService.withdraw(request.getWalletId(), request.getAmount());
            default -> throw new InvalidOperationException(request.getType());
        }

        BigDecimal newBalance = walletService.getBalance(request.getWalletId());

        return ResponseEntity.ok(new WalletResponse(request.getWalletId(), newBalance));
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
