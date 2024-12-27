package com.abasiemeka.eventsdrivenwalletapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
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

