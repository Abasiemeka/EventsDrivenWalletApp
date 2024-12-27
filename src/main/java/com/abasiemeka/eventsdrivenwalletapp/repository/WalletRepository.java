package com.abasiemeka.eventsdrivenwalletapp.repository;

import com.abasiemeka.eventsdrivenwalletapp.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}

