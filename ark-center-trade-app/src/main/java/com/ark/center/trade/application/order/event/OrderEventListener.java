package com.ark.center.trade.application.order.event;

import com.ark.center.trade.application.pay.event.PayOrderCreatedEvent;
import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.infra.order.service.OrderService;
import com.ark.center.trade.infra.order.stm.OrderStateMachine;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 角色API权限变更事件监听器
 * @author JC
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class OrderEventListener {

    private final OrderService orderService;
    private final OrderStateMachine orderStateMachine;

    /**
     * 订单生成后，把用户对应的购物车商品删除。
     * 把该事件监听放在cart模块，与order解耦
     */
    @EventListener
    public void onOrderCreated(@NotNull PayOrderCreatedEvent event) {

        PayOrderCreateDTO dto = event.getPayOrder();
        log.info("Order {} paid", dto);

        String bizTradeNo = dto.getBizTradeNo();
        Order order = orderService.byNo(bizTradeNo);
        orderStateMachine.pay(order.getId(), null);

    }


}
