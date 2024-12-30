package com.abasiemeka.eventsdrivenwalletapp.repository;

import com.abasiemeka.eventsdrivenwalletapp.model.Transaction;
import com.abasiemeka.eventsdrivenwalletapp.model.Wallet;
import com.abasiemeka.eventsdrivenwalletapp.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletIdOrderByTimestampDesc(Long walletId);
    List<Transaction> findTransactionsByTypeOrderByTimestampDesc(TransactionType transactionType);
    List<Transaction> findByWalletOrderByTimestampDesc(Wallet wallet);
}

