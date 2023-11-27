package com.ark.center.trade.application.order;

import com.ark.center.pay.api.dto.mq.PayNotifyMessage;
import com.ark.center.pay.api.dto.mq.PayOrderCreatedMessage;
import com.ark.center.trade.application.order.executor.OrderCreateCmdExe;
import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderDeliverCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.info.OrderDetailsDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.client.order.query.UserOrderPageQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.PayStatus;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.infra.order.stm.TradeOrderStateMachine;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PageResponse<OrderDTO> queryPages(OrderPageQry qry) {
        return orderQryExe.queryPages(qry);
    }
    public PageResponse<OrderDTO> queryUserOrderPages(UserOrderPageQry qry) {
        OrderPageQry pageQry = new OrderPageQry();
        pageQry.setBuyerId(ServiceContext.getCurrentUser().getUserId());
        pageQry.setOrderStatus(qry.getOrderStatus());
        pageQry.setPayStatus(qry.getPayStatus());
        pageQry.setCode(qry.getCode());
        return orderQryExe.queryPages(pageQry);
    }

    public OrderDetailsDTO queryDetails(Long id) {
        return orderQryExe.queryDetails(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void orderPay(PayNotifyMessage message) {
        tradeOrderStateMachine.pay(message.getBizOrderId(),
                updateOrder -> updateOrder.setPayStatus(PayStatus.PAY_SUCCESS.getValue()));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void onPayOrderCreated(PayOrderCreatedMessage message) {
        Long orderId = message.getBizOrderId();
        Order order = orderGateway.selectById(orderId);
        if (order == null) {
            log.warn("订单不存在 {}", orderId);
            return;
        }
        Order updateOrder = new Order();
        updateOrder.setId(orderId);
        updateOrder.setPayTradeNo(message.getPayTradeNo());
        updateOrder.setPayTypeCode(message.getPayTypeCode());
        orderGateway.update(updateOrder);

    }

    @Transactional(rollbackFor = Throwable.class)
    public void deliver(OrderDeliverCmd cmd) {
        log.info("订单 [{}] 发货, Params = {}", cmd.getOrderId(), cmd);
        tradeOrderStateMachine.deliver(cmd.getOrderId(),
                updateOrder -> {
                    updateOrder.setLogisticsCode(cmd.getLogisticsCode());
                    updateOrder.setLogisticsCompany(cmd.getLogisticsCompany());
                });

    }
}
