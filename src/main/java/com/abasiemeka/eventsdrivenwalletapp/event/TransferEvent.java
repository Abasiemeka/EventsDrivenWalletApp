package com.abasiemeka.eventsdrivenwalletapp.event;

import com.abasiemeka.eventsdrivenwalletapp.dto.TransferDto;
import org.springframework.context.ApplicationEvent;

public class TransferEvent extends ApplicationEvent {
    private Long senderId;
    private TransferDto transferDto;

    public TransferEvent(Object source, Long senderId, TransferDto transferDto) {
        super(source);
        this.senderId = senderId;
        this.transferDto = transferDto;
    }
    
    public Long getSenderId() {
	    return this.senderId;
    }
    
    public TransferDto getTransferDto() {
	    return this.transferDto;
    }
    
    public void setTransferDto(TransferDto transferDto) {
	    this.transferDto = transferDto;
    }
    
    public void setSenderId(Long senderId) {
	    this.senderId = senderId;
    }
}

