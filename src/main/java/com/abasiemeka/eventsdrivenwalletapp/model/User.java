package com.abasiemeka.eventsdrivenwalletapp.model;

import com.abasiemeka.eventsdrivenwalletapp.model.enums.UserRole;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;
}

