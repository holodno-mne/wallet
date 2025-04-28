package com.exp.self.wallet.service;

import com.exp.self.wallet.entity.Wallet;
import com.exp.self.wallet.exception.InsufficientFundsException;
import com.exp.self.wallet.exception.ServiceUnavailableException;
import com.exp.self.wallet.exception.WalletNotFoundException;
import com.exp.self.wallet.repository.WalletRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@CircuitBreaker(name = "walletService", fallbackMethod = "fallbackMethod")
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 100),
            retryFor = {
                    OptimisticLockingFailureException.class,
                    PessimisticLockingFailureException.class
            }
    )
    @Transactional
    public Wallet deposit(UUID walletId, BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));

        wallet.setBalance(amount.add(wallet.getBalance()));
        return walletRepository.save(wallet);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100))
    @Transactional
    public Wallet withdraw(UUID walletId, BigDecimal amount) {

        Optional<Wallet> walletOpt = walletRepository.findByIdSkipLocked(walletId);

        Wallet wallet = walletOpt.orElseGet(() ->
                walletRepository.findByIdForUpdate(walletId)
                        .orElseThrow(() -> new WalletNotFoundException(walletId))
        );

        if (!wallet.hasSufficientFounds(amount)) {
            throw new InsufficientFundsException(walletId, amount, wallet.getBalance());
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletRepository.save(wallet);
    }

    private Wallet fallbackHandler(UUID walletId, long amount, CallNotPermittedException ex) {
        throw new ServiceUnavailableException("The service is temporarily unavailable. Try again later");
    }

    public BigDecimal getBalance(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException(walletId)).getBalance();
    }

}
