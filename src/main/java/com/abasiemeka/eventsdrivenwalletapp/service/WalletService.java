package com.abasiemeka.eventsdrivenwalletapp.service;

import com.abasiemeka.eventsdrivenwalletapp.event.TransferEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.WalletFundingEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.WithdrawalEvent;
import com.abasiemeka.eventsdrivenwalletapp.model.Transaction;
import com.abasiemeka.eventsdrivenwalletapp.model.TransactionType;
import com.abasiemeka.eventsdrivenwalletapp.model.User;
import com.abasiemeka.eventsdrivenwalletapp.model.Wallet;
import com.abasiemeka.eventsdrivenwalletapp.repository.TransactionRepository;
import com.abasiemeka.eventsdrivenwalletapp.repository.UserRepository;
import com.abasiemeka.eventsdrivenwalletapp.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @EventListener
    @Transactional
    public void handleWalletFunding(WalletFundingEvent event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Wallet wallet = user.getWallet();
        BigDecimal amount = event.getWalletFundingDto().amount();

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.FUNDING);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Wallet funding");
        transactionRepository.save(transaction);
    }

    @EventListener
    @Transactional
    public void handleTransfer(TransferEvent event) {
        User sender = userRepository.findById(event.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User recipient = userRepository.findById(event.getTransferDto().recipientId())
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        Wallet senderWallet = sender.getWallet();
        Wallet recipientWallet = recipient.getWallet();
        BigDecimal amount = event.getTransferDto().amount();

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        recipientWallet.setBalance(recipientWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);

        Transaction senderTransaction = new Transaction();
        senderTransaction.setWallet(senderWallet);
        senderTransaction.setAmount(amount.negate());
        senderTransaction.setType(TransactionType.TRANSFER_OUT);
        senderTransaction.setTimestamp(LocalDateTime.now());
        senderTransaction.setDescription("Transfer to " + recipient.getEmail());
        transactionRepository.save(senderTransaction);

        Transaction recipientTransaction = new Transaction();
        recipientTransaction.setWallet(recipientWallet);
        recipientTransaction.setAmount(amount);
        recipientTransaction.setType(TransactionType.TRANSFER_IN);
        recipientTransaction.setTimestamp(LocalDateTime.now());
        recipientTransaction.setDescription("Transfer from " + sender.getEmail());
        transactionRepository.save(recipientTransaction);
    }

    @EventListener
    @Transactional
    public void handleWithdrawal(WithdrawalEvent event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Wallet wallet = user.getWallet();
        BigDecimal amount = event.getWithdrawalDto().amount();

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount.negate());
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Wallet withdrawal");
        transactionRepository.save(transaction);
    }
}

