package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.LoginDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoginEvent extends ApplicationEvent {
    private final LoginDto loginDto;

    public LoginEvent(Object source, LoginDto loginDto) {
        super(source);
        this.loginDto = loginDto;
    }
}

