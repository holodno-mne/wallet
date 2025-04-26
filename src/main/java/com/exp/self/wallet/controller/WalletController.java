package com.exp.self.wallet.controller;

import com.exp.self.wallet.dto.WalletRequest;
import com.exp.self.wallet.dto.WalletResponse;
import com.exp.self.wallet.exception.InvalidOperationException;
import com.exp.self.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        switch (request.getType().name()) {
            case "DEPOSIT":
                walletService.deposit(request.getWalletId(), request.getAmount());
                break;
            case "WITHDRAW":
                walletService.withdraw(request.getWalletId(), request.getAmount());
                break;
            default:
                throw new InvalidOperationException(request.getType().name());
        }

        long newBalance = walletService.getBalance(request.getWalletId());

        return ResponseEntity.ok(new WalletResponse(request.getWalletId(), newBalance));
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getBalance(
            @PathVariable UUID walletId) {

        long balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(
                new WalletResponse(walletId, balance)
        );


    }
}
