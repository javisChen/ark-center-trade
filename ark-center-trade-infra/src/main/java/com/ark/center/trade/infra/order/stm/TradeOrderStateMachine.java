package com.ark.center.trade.infra.order.stm;

import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.domain.order.OrderEvent;
import com.ark.center.trade.domain.order.OrderStatus;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.statemachine.core.StateMachine;
import com.ark.component.statemachine.core.StateMachineFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TradeOrderStateMachine implements InitializingBean {

    private final StateMachineFactory stateMachineFactory;

    public final static String TRADE_ORDER_MACHINE = "trade_order";


    private StateMachine<OrderStatus, OrderEvent> stateMachine;

    /**
     * 创建订单
     */
    public void create(String bizId, OrderCreateCmd orderCreateCmd) {
        this.stateMachine.init(bizId, OrderEvent.CREATE, orderCreateCmd);
    }

    /**
     * 支付
     */
    public OrderStatus pay(OrderStatus source) {
        return next(source, OrderEvent.PAY);
    }

    /**
     * 支付
     */
    public OrderStatus deliver(OrderStatus source) {
        return next(source, OrderEvent.DELIVER);
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
    public void afterPropertiesSet() throws Exception {
        stateMachine = stateMachineFactory.get(TRADE_ORDER_MACHINE);
    }
}
