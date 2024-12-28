package com.abasiemeka.eventsdrivenwalletapp.service;

import com.abasiemeka.eventsdrivenwalletapp.dto.LoginDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.UserRegistrationDto;
import com.abasiemeka.eventsdrivenwalletapp.event.LoginEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.UserRegistrationEvent;
import com.abasiemeka.eventsdrivenwalletapp.model.User;
import com.abasiemeka.eventsdrivenwalletapp.model.enums.UserRole;
import com.abasiemeka.eventsdrivenwalletapp.model.Wallet;
import com.abasiemeka.eventsdrivenwalletapp.model.enums.WalletTier;
import com.abasiemeka.eventsdrivenwalletapp.repository.UserRepository;
import com.abasiemeka.eventsdrivenwalletapp.security.JwtTokenProvider;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@EventListener(defaultExecution = false)
    @Transactional
    public void handleUserRegistration(UserRegistrationEvent event) {
        UserRegistrationDto dto = event.getUserRegistrationDto();
        
        // TODO: Implement BVN validation (mocked for now)
        if (!validateBvn(dto.bvn())) {
            throw new IllegalArgumentException("Invalid BVN");
        }

        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setMiddleName(dto.middleName());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setDateOfBirth(dto.dateOfBirth());
        user.setAddress(dto.address());
        user.setBvn(dto.bvn());
        user.setRole(UserRole.ROLE_USER);

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setTier(WalletTier.BASIC);
        wallet.setDailyLimit(BigDecimal.valueOf(50000));
        wallet.setWeeklyLimit(BigDecimal.valueOf(100000));

        user.setWallet(wallet);

        userRepository.save(user);
    }

    @EventListener(defaultExecution = false)
    public String handleLogin(LoginEvent event) {
        LoginDto dto = event.getLoginDto();
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return jwtTokenProvider.createToken(user.getEmail(), user.getId(), user.getRole());
    }

    private boolean validateBvn(String bvn) {
        // TODO: Implement actual BVN validation logic
        
        String trimmedBvn = bvn.trim();
        return trimmedBvn.length() == 11 && trimmedBvn.matches("\\d");  // Mocked implementation
    }
}

