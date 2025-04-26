package com.exp.self.wallet.repository;

import com.exp.self.wallet.entity.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.id = :id")
    Optional<Wallet> findByIdForUpdate(@Param("id") UUID id);

    Optional<Wallet> findById(UUID id);

    @Query(value = "SELECT * FROM wallet WHERE id = :id FOR UPDATE SKIP LOCKED",
            nativeQuery = true)
    Optional<Wallet> findByIdSkipLocked(@Param("id") UUID id);

    boolean existsById(UUID id);
}
