package com.abasiemeka.eventsdrivenwalletapp.service;

import com.abasiemeka.eventsdrivenwalletapp.dto.TransactionHistoryDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.TransferDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.WalletFundingDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.WithdrawalDto;
import com.abasiemeka.eventsdrivenwalletapp.event.TransferEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.WalletFundingEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.WithdrawalEvent;
import com.abasiemeka.eventsdrivenwalletapp.model.Transaction;
import com.abasiemeka.eventsdrivenwalletapp.model.User;
import com.abasiemeka.eventsdrivenwalletapp.model.Wallet;
import com.abasiemeka.eventsdrivenwalletapp.model.enums.TransactionType;
import com.abasiemeka.eventsdrivenwalletapp.repository.TransactionRepository;
import com.abasiemeka.eventsdrivenwalletapp.repository.UserRepository;
import com.abasiemeka.eventsdrivenwalletapp.repository.WalletRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
	
	public WalletService(WalletRepository walletRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
		this.walletRepository = walletRepository;
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
	}
	
    @EventListener
	@Transactional
    public void fundWallet(WalletFundingEvent event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Wallet wallet = user.getWallet();
        WalletFundingDto dto = event.getWalletFundingDto();
        
        wallet.setBalance(wallet.getBalance().add(dto.amount()));
        walletRepository.save(wallet);
        
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(dto.amount());
        transaction.setType(TransactionType.FUNDING);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Wallet funding");
        transactionRepository.save(transaction);
    }
    
    @EventListener
    @Transactional
    public void transferFunds(TransferEvent event) {
        User sender = userRepository.findById(event.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        TransferDto dto = event.getTransferDto();
        User recipient = userRepository.findByEmail(dto.recipientEmail())
                .orElseThrow(() -> new RuntimeException("Recipient not found"));
        
        Wallet senderWallet = sender.getWallet();
        Wallet recipientWallet = recipient.getWallet();
        
        if (senderWallet.getBalance().compareTo(dto.amount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        
        senderWallet.setBalance(senderWallet.getBalance().subtract(dto.amount()));
        recipientWallet.setBalance(recipientWallet.getBalance().add(dto.amount()));
        
        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);
        
        Transaction senderTransaction = new Transaction();
        senderTransaction.setWallet(senderWallet);
        senderTransaction.setAmount(dto.amount().negate());
        senderTransaction.setType(TransactionType.TRANSFER_OUT);
        senderTransaction.setTimestamp(LocalDateTime.now());
        senderTransaction.setDescription("Transfer to " + recipient.getEmail());
        transactionRepository.save(senderTransaction);
        
        Transaction recipientTransaction = new Transaction();
        recipientTransaction.setWallet(recipientWallet);
        recipientTransaction.setAmount(dto.amount());
        recipientTransaction.setType(TransactionType.TRANSFER_IN);
        recipientTransaction.setTimestamp(LocalDateTime.now());
        recipientTransaction.setDescription("Transfer from " + sender.getEmail());
        transactionRepository.save(recipientTransaction);
    }
    
    @EventListener
    @Transactional
    public void withdrawFunds(WithdrawalEvent event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Wallet wallet = user.getWallet();
        WithdrawalDto dto = event.getWithdrawalDto();
        
        if (wallet.getBalance().compareTo(dto.amount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        
        wallet.setBalance(wallet.getBalance().subtract(dto.amount()));
        walletRepository.save(wallet);
        
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(dto.amount().negate());
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Wallet withdrawal");
        transactionRepository.save(transaction);
    }
    
    public BigDecimal getWalletBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getWallet().getBalance();
    }
    
    public List<TransactionHistoryDto> getTransactionHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Wallet wallet = user.getWallet();
        
        List<Transaction> transactions = transactionRepository.findByWalletOrderByTimestampDesc(wallet);
        
        return transactions.stream()
                .map(this::mapToTransactionHistoryDto)
                .collect(Collectors.toList());
    }
    
    private TransactionHistoryDto mapToTransactionHistoryDto(Transaction transaction) {
        return new TransactionHistoryDto(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getTimestamp(),
                transaction.getDescription()
        );
    }
}

