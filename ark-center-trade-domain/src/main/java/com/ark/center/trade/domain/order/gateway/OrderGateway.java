package com.ark.center.trade.domain.order.gateway;

import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.component.dto.PageResponse;

import java.util.List;

public interface OrderGateway {

    void save(Order order);

    PageResponse<OrderDTO> getPageList(OrderPageQry pageQry);

    OrderDTO findById(Long orderId);

    List<OrderItemDTO> listOrderItems(Long orderId);

}
