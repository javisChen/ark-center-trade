package com.ark.center.trade.application.order;

import com.ark.center.pay.api.dto.mq.PayNotifyMessage;
import com.ark.center.pay.api.dto.mq.PayOrderCreatedMessage;
import com.ark.center.trade.application.order.executor.OrderCreateCmdExe;
import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderDeliverCmd;
import com.ark.center.trade.client.order.command.OrderReceiveCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderDetailsQry;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.client.order.query.UserOrderPageQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.enums.PayStatus;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.infra.order.stm.TradeOrderStateMachine;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderAppService {

    private final OrderCreateCmdExe orderCreateCmdExe;
    private final OrderQryExe orderQryExe;
    private final OrderGateway orderGateway;
    private final TradeOrderStateMachine tradeOrderStateMachine;

    @Transactional(rollbackFor = Throwable.class)
    public Long createOrder(OrderCreateCmd orderCreateCmd) {
        return orderCreateCmdExe.execute(orderCreateCmd);
    }

    /**
     * 查询订单列表
     */
    public PageResponse<OrderDTO> queryPages(OrderQry qry) {
        qry.setWithOrderItems(false);
        qry.setWithReceive(false);
        return orderQryExe.queryPages(qry);
    }

    /**
     * 查询用户的订单列表
     */
    public PageResponse<OrderDTO> queryUserOrderPages(UserOrderPageQry qry) {
        OrderQry orderQry = new OrderQry();
        orderQry.setBuyerId(ServiceContext.getCurrentUser().getUserId());
        orderQry.setOrderStatus(qry.getOrderStatus());
        orderQry.setPayStatus(qry.getPayStatus());
        orderQry.setTradeNo(qry.getTradeNo());
        orderQry.setCurrent(qry.getCurrent());
        orderQry.setSize(qry.getSize());
        orderQry.setWithOrderItems(true);
        return orderQryExe.queryPages(orderQry);
    }

    /**
     * 查询订单详情
     */
    public OrderDTO queryDetails(OrderDetailsQry qry) {
        return orderQryExe.queryDetails(qry);
    }

    /**
     * 订单支付
     */
    @Transactional(rollbackFor = Throwable.class)
    public void pay(PayNotifyMessage message) {
        String bizTradeNo = message.getBizTradeNo();
        Order order = orderGateway.selectByTradeNo(bizTradeNo);
        if (order == null) {
            log.warn("订单不存在 {}", bizTradeNo);
            return;
        }
        tradeOrderStateMachine.pay(order.getId(),
                updateOrder -> {
                    updateOrder.setPayTime(LocalDateTime.now());
                    updateOrder.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
                });
    }

    @Transactional(rollbackFor = Throwable.class)
    public void onPayOrderCreated(PayOrderCreatedMessage message) {
        String tradeNo = message.getBizTradeNo();
        if (StringUtils.isBlank(tradeNo)) {
            log.warn("订单号为空");
            return;
        }
        Order order = orderGateway.selectByTradeNo(tradeNo);
        if (order == null) {
            log.warn("订单不存在 {}", tradeNo);
            return;
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setPayTradeNo(message.getPayTradeNo());
        updateOrder.setPayType(message.getPayTypeId());
        updateOrder.setPayStatus(PayStatus.PAYING.getValue());
        orderGateway.update(updateOrder);

    }

    @Transactional(rollbackFor = Throwable.class)
    public void deliver(OrderDeliverCmd cmd) {
        log.info("Order [{}] starting deliver, params = {}", cmd.getOrderId(), cmd);
        tradeOrderStateMachine.deliver(cmd.getOrderId(),
                updateOrder -> {
                    updateOrder.setDeliverTime(LocalDateTime.now());
                    updateOrder.setLogisticsCode(cmd.getLogisticsCode());
                    updateOrder.setLogisticsCompany(cmd.getLogisticsCompany());
                });

    }

    public void receive(OrderReceiveCmd cmd) {
        log.info("Order [{}] starting receive", cmd.getOrderId());
        tradeOrderStateMachine.receive(cmd.getOrderId(),
                updateOrder -> {
                    updateOrder.setReceiveTime(LocalDateTime.now());
                });
    }
}
