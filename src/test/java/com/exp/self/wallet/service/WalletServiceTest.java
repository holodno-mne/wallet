package com.exp.self.wallet.service;

import com.exp.self.wallet.entity.Wallet;
import com.exp.self.wallet.exception.InsufficientFundsException;
import com.exp.self.wallet.exception.WalletNotFoundException;
import com.exp.self.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletService walletService;
    private final UUID walletId = UUID.randomUUID();

    @Test
    void deposit_ShouldIncreaseBalance() {
        Wallet wallet = new Wallet(walletId, 500L);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        walletService.deposit(walletId, 300L);

        assertEquals(800L, wallet.getBalance());
        verify(walletRepository).save(wallet);
    }

    @Test
    void withdraw_ShouldThrowException_IfInsufficientFunds() {
        Wallet wallet = new Wallet(walletId, 200L);
        when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class, () ->
                walletService.withdraw(walletId, 300L));
    }
}