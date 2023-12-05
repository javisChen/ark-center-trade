package com.ark.center.trade.application.order.event;

import com.ark.center.trade.domain.cart.service.CartService;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 角色API权限变更事件监听器
 * @author JC
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCreatedEventListener implements ApplicationListener<OrderCreatedEvent> {

    private final OrderGateway orderGateway;
    private final CartService cartService;

    public void onApplicationEvent(@NotNull OrderCreatedEvent event) {
        Order order = event.getOrder();
        log.info("Order {} created", order.getId());

    }

}
