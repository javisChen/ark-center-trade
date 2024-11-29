package com.ark.center.trade.application.order;

import com.ark.center.trade.application.order.executor.OrderCreateCmdExe;
import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderDeliverCmd;
import com.ark.center.trade.client.order.command.OrderReceiveCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.client.pay.mq.PayOrderChangedEventDTO;
import com.ark.center.trade.infra.order.Order;
import com.ark.center.trade.infra.order.constants.PayStatus;
import com.ark.center.trade.infra.order.service.OrderService;
import com.ark.center.trade.infra.order.stm.OrderStateMachine;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCommandHandler {

    private final OrderCreateCmdExe orderCreateCmdExe;
    private final OrderQryExe orderQryExe;
    private final OrderService orderService;
    private final OrderStateMachine orderStateMachine;

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
     * 支付单状态发生变更
     */
    @Transactional(rollbackFor = Throwable.class)
    public void handlePayOrderStatusChanged(PayOrderChangedEventDTO eventDTO) {
        String bizTradeNo = eventDTO.getBizTradeNo();
        Order order = orderService.byTradeNo(bizTradeNo);
        if (order == null) {
            return;
        }
        orderStateMachine.pay(order.getId(),
                updateOrder -> {
                    updateOrder.setPayTime(LocalDateTime.now());
                    updateOrder.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
                });
    }

    /**
     * 支付单已创建
     */
    @Transactional(rollbackFor = Throwable.class)
    public void onPayOrderCreated(PayOrderChangedEventDTO message) {
        String tradeNo = message.getBizTradeNo();
        Order order = orderService.byTradeNo(tradeNo);
        if (order == null) {
            return;
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setPayTradeNo(message.getPayTradeNo());
        updateOrder.setPayStatus(PayStatus.PAYING.getValue());
        orderService.updateById(updateOrder);

    }

    @Transactional(rollbackFor = Throwable.class)
    public void deliver(OrderDeliverCmd cmd) {
        log.info("Order [{}] starting deliver, params = {}", cmd.getOrderId(), cmd);
        orderStateMachine.deliver(cmd.getOrderId(),
                updateOrder -> {
                    updateOrder.setDeliverTime(LocalDateTime.now());
                    updateOrder.setLogisticsCode(cmd.getLogisticsCode());
                    updateOrder.setLogisticsCompany(cmd.getLogisticsCompany());
                });

    }

    public void receive(OrderReceiveCmd cmd) {
        log.info("Order [{}] starting receive", cmd.getOrderId());
        orderStateMachine.receive(cmd.getOrderId(),
                updateOrder -> {
                    updateOrder.setReceiveTime(LocalDateTime.now());
                });
    }
}
