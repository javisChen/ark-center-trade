package com.ark.center.trade.application.order.executor;

import com.ark.center.trade.application.order.assembler.OrderAssembler;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.ReceiveDTO;
import com.ark.center.trade.client.order.dto.info.OrderDetailsDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.order.gateway.ReceiveGateway;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderQryExe {

    private final OrderGateway orderGateway;
    private final ReceiveGateway receiveGateway;
    private final OrderAssembler orderAssembler;

    public PageResponse<OrderDTO> queryPages(OrderPageQry pageQry) {
        return orderGateway.selectPages(pageQry);
    }

    public OrderDetailsDTO queryDetails(Long orderId) {
        Order order = orderGateway.selectById(orderId);
        List<OrderItem> orderItems = orderGateway.selectItemsByOrderId(orderId);
        ReceiveDTO receiveDTO = receiveGateway.selectByOrderId(orderId);
        return orderAssembler.assemble(order, orderItems, receiveDTO);
    }

}
