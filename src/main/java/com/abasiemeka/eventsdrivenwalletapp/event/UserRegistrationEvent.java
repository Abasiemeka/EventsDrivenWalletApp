package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.UserRegistrationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private final UserRegistrationDto userRegistrationDto;

    public UserRegistrationEvent(Object source, UserRegistrationDto userRegistrationDto) {
        super(source);
        this.userRegistrationDto = userRegistrationDto;
    }
}

