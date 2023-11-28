package com.ark.center.trade.domain.receive.gateway;

import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.domain.order.model.Receive;

import java.util.List;

public interface ReceiveGateway {

    void save(Receive receive);

    ReceiveDTO selectByOrderId(Long orderId);

    List<ReceiveDTO> selectByOrderIds(List<Long> orderId);
}
