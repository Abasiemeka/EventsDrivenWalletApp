package com.abasiemeka.eventsdrivenwalletapp.controller;

import com.abasiemeka.eventsdrivenwalletapp.dto.LoginDto;
import com.abasiemeka.eventsdrivenwalletapp.dto.UserRegistrationDto;
import com.abasiemeka.eventsdrivenwalletapp.event.LoginEvent;
import com.abasiemeka.eventsdrivenwalletapp.event.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final ApplicationEventPublisher eventPublisher;
	
	public UserController(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	@PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        eventPublisher.publishEvent(new UserRegistrationEvent(this, userRegistrationDto));
        return ResponseEntity.ok("User registration event published");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        eventPublisher.publishEvent(new LoginEvent(this, loginDto));
        return ResponseEntity.ok("Login event published");
    }
}

