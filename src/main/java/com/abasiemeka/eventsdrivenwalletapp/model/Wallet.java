package com.abasiemeka.eventsdrivenwalletapp.model;

import com.abasiemeka.eventsdrivenwalletapp.model.enums.WalletTier;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "wallet")
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private WalletTier tier;

    private BigDecimal dailyLimit;
    private BigDecimal weeklyLimit;
}

