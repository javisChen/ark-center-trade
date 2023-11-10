package com.ark.center.trade.infra.config;

import com.ark.center.trade.domain.order.OrderEvent;
import com.ark.center.trade.domain.order.OrderStatus;
import com.ark.center.trade.infra.order.stm.TradeOrderStateMachine;
import com.ark.component.statemachine.core.StateMachine;
import com.ark.component.statemachine.core.builder.StateMachineBuilder;
import com.ark.component.statemachine.core.lock.DefaultStateMachineLock;
import com.ark.component.statemachine.core.persist.JdbcStateMachinePersist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.EnumSet;

@Configuration
public class StateMachineConfiguration {

    @Bean
    public StateMachine<OrderStatus, OrderEvent> tradeOrderStatusMachine(JdbcTemplate jdbcTemplate) {
        StateMachineBuilder<OrderStatus, OrderEvent> builder = StateMachineBuilder.newBuilder();
        return builder
                // 状态机基本配置
                .withConfiguration(configurationBuilder -> configurationBuilder
                        .machineId(TradeOrderStateMachine.TRADE_ORDER_MACHINE)
                        .persist(new JdbcStateMachinePersist<>(jdbcTemplate))
                        .lock(new DefaultStateMachineLock<>())
                )
                // 状态配置
                .withStates(stateBuilder -> stateBuilder
                        .states(EnumSet.allOf(OrderStatus.class))
                        .initial(OrderStatus.WAIT_PAY)
                        .end(OrderStatus.COMPLETED))
                // 流转配置
                .withTransition(transitionBuilder -> transitionBuilder
                        .withExternal()
                        .name("待支付 -> 待发货")
                        .source(OrderStatus.WAIT_PAY)
                        .event(OrderEvent.PAY)
                        .target(OrderStatus.WAIT_DELIVER)
                        .and().withExternal()
                        .name("待发货 -> 待收货")
                        .source(OrderStatus.WAIT_DELIVER)
                        .event(OrderEvent.DELIVER)
                        .target(OrderStatus.WAIT_RECEIVE)
                        .and().withExternal()
                        .name("待收货 -> 待评价")
                        .source(OrderStatus.WAIT_RECEIVE)
                        .event(OrderEvent.RECEIVE)
                        .target(OrderStatus.WAIT_EVALUATE)
                        .and().withExternal()
                        .name("待评价 -> 已完成")
                        .source(OrderStatus.WAIT_EVALUATE)
                        .event(OrderEvent.EVALUATE)
                        .target(OrderStatus.COMPLETED)
                ).build();
    }
}
