package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.LoginDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class LoginEvent extends ApplicationEvent {
    private LoginDto loginDto;

    public LoginEvent(Object source, LoginDto loginDto) {
        super(source);
        this.loginDto = loginDto;
    }
    
    public LoginDto getLoginDto() {
	    return this.loginDto;
    }
    
    public void setLoginDto(LoginDto loginDto) {
	    this.loginDto = loginDto;
    }
}

