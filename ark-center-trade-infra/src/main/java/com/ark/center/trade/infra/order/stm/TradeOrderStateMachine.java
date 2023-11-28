package com.ark.center.trade.infra.order.stm;

import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderEvent;
import com.ark.center.trade.domain.order.OrderStatus;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.statemachine.core.StateMachine;
import com.ark.component.statemachine.core.StateMachineFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class TradeOrderStateMachine implements InitializingBean {

    private final StateMachineFactory stateMachineFactory;
    private final OrderGateway orderGateway;

    public final static String TRADE_ORDER_MACHINE = "trade_order";


    private StateMachine<OrderStatus, OrderEvent> stateMachine;

    /**
     * 支付
     */
    public void pay(Long orderId, Consumer<Order> updateOrderConsumer) {
        execute(orderId, OrderEvent.PAY, updateOrderConsumer);

    }

    /**
     * 发货
     */
    public void deliver(Long orderId, Consumer<Order> updateOrderConsumer) {
        execute(orderId, OrderEvent.DELIVER, updateOrderConsumer);
    }

    /**
     * 执行订单状态变更
     *
     * @param orderId 订单id
     * @param event 触发的事件
     * @param updateOrderConsumer 如果需要更新其他order的字段，可以通过该consumer来set值进去
     */
    private void execute(Long orderId, OrderEvent event, Consumer<Order> updateOrderConsumer) {
        Order sourceOrder = orderGateway.selectById(orderId);
        if (sourceOrder == null) {
            log.warn("订单不存在 {}", orderId);
            return;
        }

        OrderStatus currentStatus = OrderStatus.getByValue(sourceOrder.getOrderStatus());
        OrderStatus nextStatus = next(currentStatus, event);

        Order updateOrder = new Order();
        updateOrder.setOrderStatus(nextStatus.getValue());

        if (updateOrderConsumer != null) {
            updateOrderConsumer.accept(updateOrder);
        }

        int updated = orderGateway.optimisticLockUpdateOrderStatusAndOthers(sourceOrder, updateOrder);

        if (updated == 0) {
            log.warn("订单 [{}] 已发生改变，更新失败", orderId);
        }

    }

    private OrderStatus next(OrderStatus source, OrderEvent event) {
        try {
            return this.stateMachine.nextState(source, event, null);
        } catch (Exception e) {
            log.error("状态机流转失败", e);
            throw ExceptionFactory.userException("订单状态变更失败");
        }
    }

    @Override
    public void afterPropertiesSet() {
        stateMachine = stateMachineFactory.get(TRADE_ORDER_MACHINE);
    }
}
