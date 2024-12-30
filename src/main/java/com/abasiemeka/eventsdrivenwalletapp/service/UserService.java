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
import com.abasiemeka.eventsdrivenwalletapp.repository.WalletRepository;
import com.abasiemeka.eventsdrivenwalletapp.security.JwtTokenProvider;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final WalletRepository walletRepository;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, WalletRepository walletRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.walletRepository = walletRepository;
	}
	
	@EventListener(defaultExecution = false)
	@Transactional
	public User registerUser(UserRegistrationEvent event) {
		UserRegistrationDto dto = event.getUserRegistrationDto();
		if (userRepository.existsByEmail(dto.email())) {
			throw new RuntimeException("Email already exists");
		}
		
		// Mock BVN validation
		if (!validateBvn(dto.bvn())) {
			throw new RuntimeException("Invalid BVN");
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
		
		User savedUser = userRepository.save(user);
		
		Wallet wallet = new Wallet();
		wallet.setUser(savedUser);
		wallet.setBalance(BigDecimal.ZERO);
		wallet.setTier(WalletTier.BASIC);
		wallet.setDailyLimit(new BigDecimal("1000"));
		wallet.setWeeklyLimit(new BigDecimal("5000"));
		walletRepository.save(wallet);
		
		savedUser.setWallet(wallet);
		return userRepository.save(savedUser);
	}
	
	private boolean validateBvn(String bvn) {
		// Mock BVN validation
		return bvn != null && bvn.length() == 11;
	}
	
	public User loginUser(LoginEvent event) {
		LoginDto dto = event.getLoginDto();
		User user = userRepository.findByEmail(dto.email())
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		
		return user;
	}
	
	public Long getUserIdFromUserDetails(UserDetails userDetails) {
		if (userDetails == null) {
			throw new IllegalArgumentException("UserDetails cannot be null");
		}
		
		User user = userRepository.findByEmail(userDetails.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userDetails.getUsername()));
		
		return user.getId();
	}
}


