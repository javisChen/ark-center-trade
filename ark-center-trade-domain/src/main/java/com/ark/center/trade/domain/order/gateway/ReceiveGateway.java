package com.ark.center.trade.domain.order.gateway;

import com.ark.center.trade.client.order.dto.ReceiveDTO;
import com.ark.center.trade.domain.order.model.Receive;

public interface ReceiveGateway {

    void save(Receive receive);

    ReceiveDTO findByOrderId(Long orderId);
}
