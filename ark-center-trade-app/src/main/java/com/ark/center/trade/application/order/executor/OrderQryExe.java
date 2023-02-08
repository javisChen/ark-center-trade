package com.ark.center.trade.application.order.executor;

import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderQryExe {

    private final OrderGateway orderGateway;

    public PageResponse<OrderDTO> getPageList(OrderPageQry pageQry) {
        return orderGateway.getPageList(pageQry);
    }

    public OrderDTO get(Long orderId) {
        return orderGateway.findById(orderId);
    }

}
