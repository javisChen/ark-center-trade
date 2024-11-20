package com.ark.center.trade.application.cart.event;

import com.ark.center.trade.application.order.event.OrderCreatedEvent;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.domain.cart.service.CartService;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.infra.order.service.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartEventListener {

    private final OrderService orderGateway;
    private final CartService cartService;

    /**
     * 订单生成后，把用户对应的购物车商品删除。
     * 把该事件监听放在cart模块，与order解耦
     */
    @EventListener
    public void onOrderCreated(@NotNull OrderCreatedEvent event) {

        Order order = event.getOrder();
        log.info("Order {} created，try to empty cart", order.getId());

        clearCarts(order);
    }

    private void clearCarts(Order order) {
        Long orderId = order.getId();
        Long buyerId = order.getBuyerId();
        List<OrderItemDTO> orderItems = orderGateway.selectOrderItems(orderId);
        List<Long> skuIds = orderItems.stream().map(OrderItemDTO::getSkuId).toList();
        cartService.removeBuyerCartItems(buyerId, skuIds);
    }
}
