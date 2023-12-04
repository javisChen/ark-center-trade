package com.ark.center.trade.application.order.event;

import com.ark.center.trade.domain.order.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 角色API权限变更事件
 * @author JC
 */
@Getter
@Setter
public class OrderCreatedEvent extends ApplicationEvent {

    private final Order order;

    public OrderCreatedEvent(Object source, Order order) {
        super(source, Clock.systemUTC());
        this.order = order;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

}
