package com.ark.center.trade.application.order.event;

import com.ark.center.trade.infra.order.Order;
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


    public void onApplicationEvent(@NotNull OrderCreatedEvent event) {
        Order order = event.getOrder();
        log.info("Order {} created", order.getId());

    }

}
