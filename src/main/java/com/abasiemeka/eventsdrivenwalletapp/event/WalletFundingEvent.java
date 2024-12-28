package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.WalletFundingDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


public class WalletFundingEvent extends ApplicationEvent {
    private Long userId;
    private WalletFundingDto walletFundingDto;

    public WalletFundingEvent(Object source, Long userId, WalletFundingDto walletFundingDto) {
        super(source);
        this.userId = userId;
        this.walletFundingDto = walletFundingDto;
    }
    
    public Long getUserId() {
	    return this.userId;
    }
    
    public WalletFundingDto getWalletFundingDto() {
	    return this.walletFundingDto;
    }
    
    public void setWalletFundingDto(WalletFundingDto walletFundingDto) {
	    this.walletFundingDto = walletFundingDto;
    }
    
    public void setUserId(Long userId) {
	    this.userId = userId;
    }
}

