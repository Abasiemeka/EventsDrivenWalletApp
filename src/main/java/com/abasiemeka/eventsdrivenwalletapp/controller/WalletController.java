package com.abasiemeka.eventsdrivenwalletapp.controller;

import com.abasiemeka.eventsdrivenwalletapp.dto.TransferDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.WalletFundingDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.WithdrawalDto;
import com.abasiemeka.eventsdrivenwalletapp.event.TransferEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.WalletFundingEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.WithdrawalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final ApplicationEventPublisher eventPublisher;

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
    public ResponseEntity<String> getBalance(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        // TODO: Implement balance retrieval logic
        return ResponseEntity.ok("Balance retrieval not implemented yet");
    }

    @GetMapping("/transactions")
    public ResponseEntity<String> getTransactionHistory(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        // TODO: Implement transaction history retrieval logic
        return ResponseEntity.ok("Transaction history retrieval not implemented yet");
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        // TODO: Implement logic to extract user ID from UserDetails
        return 1L; // Placeholder implementation
    }
}

