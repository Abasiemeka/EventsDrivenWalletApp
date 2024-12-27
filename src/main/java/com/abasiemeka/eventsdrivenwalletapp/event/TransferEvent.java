package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.TransferDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransferEvent extends ApplicationEvent {
    private final Long senderId;
    private final TransferDto transferDto;

    public TransferEvent(Object source, Long senderId, TransferDto transferDto) {
        super(source);
        this.senderId = senderId;
        this.transferDto = transferDto;
    }
}

