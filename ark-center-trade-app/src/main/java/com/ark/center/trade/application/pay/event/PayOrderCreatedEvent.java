package com.ark.center.trade.application.pay.event;

import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PayOrderCreatedEvent extends ApplicationEvent {

    private PayOrderCreateDTO payOrder;

    public PayOrderCreatedEvent(Object source, PayOrderCreateDTO payOrder) {
        super(source);
        this.payOrder = payOrder;
    }

}
