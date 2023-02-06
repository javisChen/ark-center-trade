package com.ark.center.trade.infra.order.gateway.impl;

import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.OrderItem;
import com.ark.center.trade.infra.order.convertor.OrderConvertor;
import com.ark.center.trade.infra.order.gateway.impl.db.OrderDO;
import com.ark.center.trade.infra.order.gateway.impl.db.OrderItemMapper;
import com.ark.center.trade.infra.order.gateway.impl.db.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public void save(Order order) {
        OrderDO orderDO = OrderConvertor.toOrderDataObject(order);
        orderMapper.insert(orderDO);

        List<OrderItem> orderItemList = order.getOrderItemList();
        orderItemList.stream()
                .map(orderItem -> OrderConvertor.toOrderItemDataObject(order, orderItem))
                .forEach(orderItemMapper::insert);
    }
}
