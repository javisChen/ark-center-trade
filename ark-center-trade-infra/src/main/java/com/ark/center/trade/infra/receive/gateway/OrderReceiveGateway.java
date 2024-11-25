package com.ark.center.trade.infra.receive.gateway;

import com.ark.center.trade.client.order.dto.OrderReceiveDTO;
import com.ark.center.trade.infra.order.OrderReceive;

import java.util.List;

public interface OrderReceiveGateway {

    void save(OrderReceive orderReceive);

    List<OrderReceiveDTO> selectByOrderIds(List<Long> orderId);

}
