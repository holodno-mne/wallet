package com.exp.self.wallet.service;

import com.exp.self.wallet.entity.Wallet;
import com.exp.self.wallet.exception.InsufficientFundsException;
import com.exp.self.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletService walletService;
    private final UUID walletId = UUID.randomUUID();

    @Test
    void deposit_ShouldIncreaseBalance() {
        Wallet wallet = new Wallet(walletId, BigDecimal.valueOf(500));
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        walletService.deposit(walletId, BigDecimal.valueOf(300));

        assertEquals(800L, wallet.getBalance());
        verify(walletRepository).save(wallet);
    }

    @Test
    void withdraw_ShouldThrowException_IfInsufficientFunds() {
        Wallet wallet = new Wallet(walletId, BigDecimal.valueOf(200));
        when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class, () ->
                walletService.withdraw(walletId, BigDecimal.valueOf(300)));
    }
}