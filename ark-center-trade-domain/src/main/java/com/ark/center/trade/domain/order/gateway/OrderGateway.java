package com.ark.center.trade.domain.order.gateway;

import com.ark.center.trade.domain.order.model.Order;

public interface OrderGateway {

    void save(Order order);

}