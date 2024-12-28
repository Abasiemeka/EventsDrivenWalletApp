package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.UserRegistrationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {
    private UserRegistrationDto userRegistrationDto;

    public UserRegistrationEvent(Object source, UserRegistrationDto userRegistrationDto) {
        super(source);
        this.userRegistrationDto = userRegistrationDto;
    }
    
    public UserRegistrationDto getUserRegistrationDto() {
	    return userRegistrationDto;
    }
    
    public void setUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
	    this.userRegistrationDto = userRegistrationDto;
    }
}

