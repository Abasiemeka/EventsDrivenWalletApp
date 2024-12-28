package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.WithdrawalDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

public class WithdrawalEvent extends ApplicationEvent {
    private Long userId;
    private WithdrawalDto withdrawalDto;

    public WithdrawalEvent(Object source, Long userId, WithdrawalDto withdrawalDto) {
        super(source);
        this.userId = userId;
        this.withdrawalDto = withdrawalDto;
    }
    
    public Long getUserId() {
	    return this.userId;
    }

    public WithdrawalDto getWithdrawalDto() {
	    return this.withdrawalDto;
    }

    public void setWithdrawalDto(WithdrawalDto withdrawalDto) {
	    this.withdrawalDto = withdrawalDto;
    }

    public void setUserId(Long userId) {
	    this.userId = userId;
    }
}

