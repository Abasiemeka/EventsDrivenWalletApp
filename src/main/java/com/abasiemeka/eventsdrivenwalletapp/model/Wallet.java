package com.abasiemeka.eventsdrivenwalletapp.model;

import com.abasiemeka.eventsdrivenwalletapp.model.enums.WalletTier;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private WalletTier tier;

    private BigDecimal dailyLimit;
    private BigDecimal weeklyLimit;
}

