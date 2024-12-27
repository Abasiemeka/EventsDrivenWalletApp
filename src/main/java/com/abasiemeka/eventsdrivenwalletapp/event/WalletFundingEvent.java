package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.WalletFundingDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class WalletFundingEvent extends ApplicationEvent {
    private final Long userId;
    private final WalletFundingDto walletFundingDto;

    public WalletFundingEvent(Object source, Long userId, WalletFundingDto walletFundingDto) {
        super(source);
        this.userId = userId;
        this.walletFundingDto = walletFundingDto;
    }
}

