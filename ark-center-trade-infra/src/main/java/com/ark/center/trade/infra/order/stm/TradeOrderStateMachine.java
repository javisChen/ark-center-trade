package com.ark.center.trade.infra.order.stm;

import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.domain.order.OrderEvent;
import com.ark.center.trade.domain.order.OrderStatus;
import com.ark.component.statemachine.core.StateMachine;
import com.ark.component.statemachine.core.StateMachineFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
        return this.stateMachine.sendEvent(source, OrderEvent.PAY, null);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        stateMachine = stateMachineFactory.get(TRADE_ORDER_MACHINE);
    }
}
