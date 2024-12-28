package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.WithdrawalDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
public class WithdrawalEvent extends ApplicationEvent {
    private final Long userId;
    private final WithdrawalDto withdrawalDto;

    public WithdrawalEvent(Object source, Long userId, WithdrawalDto withdrawalDto) {
        super(source);
        this.userId = userId;
        this.withdrawalDto = withdrawalDto;
    }
}

