package com.ark.center.trade.application.order;

import com.ark.center.pay.api.dto.mq.PayNotifyMessage;
import com.ark.center.trade.application.order.executor.OrderCreateCmdExe;
import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.info.OrderInfoDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.order.model.Order;
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

    public PageResponse<OrderDTO> getPageList(OrderPageQry qry) {
        return orderQryExe.getPageList(qry);
    }

    public OrderInfoDTO getOrder(Long id) {
        OrderInfoDTO orderDTO = orderQryExe.get(id);
        return orderDTO;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void updateOrderOnPaySuccess(PayNotifyMessage message) {
        Order order = new Order();
        order.setOrderId(message.getOrderId());
        order.paySuccess(message.getPayTradeNo(), LocalDateTime.now());
        orderGateway.updateOrderPayStatus(order);
    }
}
