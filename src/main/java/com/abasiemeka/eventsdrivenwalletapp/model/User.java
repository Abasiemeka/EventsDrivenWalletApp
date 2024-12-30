package com.abasiemeka.eventsdrivenwalletapp.model;

import com.abasiemeka.eventsdrivenwalletapp.model.enums.UserRole;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
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

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String address;
    private String bvn;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
}

