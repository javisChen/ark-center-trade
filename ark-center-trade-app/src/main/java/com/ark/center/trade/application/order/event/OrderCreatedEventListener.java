package com.ark.center.trade.application.order.event;

import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.domain.cart.service.CartService;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

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

        Long orderId = order.getId();
        Long buyerId = order.getBuyerId();
        List<OrderItemDTO> orderItems = orderGateway.selectOrderItems(orderId);
        List<Long> skuIds = orderItems.stream().map(OrderItemDTO::getSkuId).toList();
        cartService.removeBuyerCartItems(buyerId, skuIds);
    }

}
