package com.ark.center.trade.application.order;

import com.ark.center.pay.api.dto.mq.PayNotifyMessage;
import com.ark.center.trade.application.order.executor.OrderCreateCmdExe;
import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.info.OrderDetailsDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderStatus;
import com.ark.center.trade.domain.order.PayStatus;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderAppService {

    private final OrderCreateCmdExe orderCreateCmdExe;
    private final OrderQryExe orderQryExe;
    private final OrderGateway orderGateway;

    @Transactional(rollbackFor = Throwable.class)
    public Long createOrder(OrderCreateCmd orderCreateCmd) {
        return orderCreateCmdExe.execute(orderCreateCmd);
    }

    public PageResponse<OrderDTO> queryPages(OrderPageQry qry) {
        return orderQryExe.queryPages(qry);
    }

    public OrderDetailsDTO queryDetails(Long id) {
        return orderQryExe.queryDetails(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void updateOrderOnPaySuccess(PayNotifyMessage message) {
        Order order = new Order();
        order.setId(message.getOrderId());
        order.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
        order.setPayTradeNo(message.getPayTradeNo());
        order.setPayTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.COMPLETED.getValue());;
        orderGateway.updateOrderPayStatus(order);
    }
}
