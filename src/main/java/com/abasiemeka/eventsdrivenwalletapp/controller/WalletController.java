package com.abasiemeka.eventsdrivenwalletapp.controller;

import com.abasiemeka.eventsdrivenwalletapp.dto.TransactionHistoryDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.TransferDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.WalletFundingDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.WithdrawalDto;
import com.abasiemeka.eventsdrivenwalletapp.event.TransferEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.WalletFundingEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.WithdrawalEvent;
import com.abasiemeka.eventsdrivenwalletapp.service.UserService;
import com.abasiemeka.eventsdrivenwalletapp.service.WalletService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;
    private final WalletService walletService;
    
    public WalletController(ApplicationEventPublisher eventPublisher, UserService userService, WalletService walletService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.walletService = walletService;
    }
    
    @PostMapping("/fund")
    public ResponseEntity<String> fundWallet(@AuthenticationPrincipal UserDetails userDetails,
                                             @Valid @RequestBody WalletFundingDto walletFundingDto) {
        Long userId = getUserIdFromUserDetails(userDetails);
        eventPublisher.publishEvent(new WalletFundingEvent(this, userId, walletFundingDto));
        
        return ResponseEntity.ok("Wallet funding event published");
    }
    
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@AuthenticationPrincipal UserDetails userDetails,
                                                @Valid @RequestBody TransferDto transferDto) {
        Long userId = getUserIdFromUserDetails(userDetails);
        eventPublisher.publishEvent(new TransferEvent(this, userId, transferDto));
        
        return ResponseEntity.ok("Transfer event published");
    }
    
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawFunds(@AuthenticationPrincipal UserDetails userDetails,
                                                @Valid @RequestBody WithdrawalDto withdrawalDto) {
        Long userId = getUserIdFromUserDetails(userDetails);
        eventPublisher.publishEvent(new WithdrawalEvent(this, userId, withdrawalDto));
        
        return ResponseEntity.ok("Withdrawal event published");
    }
    
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        BigDecimal balance = walletService.getWalletBalance(userId);
        
        return ResponseEntity.ok(balance);
    }
    
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionHistoryDto>> getTransactionHistory(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        List<TransactionHistoryDto> transactionHistory = walletService.getTransactionHistory(userId);
        
        return ResponseEntity.ok(transactionHistory);
    }
    
    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        return userService.getUserIdFromUserDetails(userDetails);
    }
}

