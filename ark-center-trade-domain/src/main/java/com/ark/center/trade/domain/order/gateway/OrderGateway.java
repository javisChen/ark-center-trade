package com.ark.center.trade.domain.order.gateway;

import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.ark.component.dto.PageResponse;

import java.util.List;

public interface OrderGateway {

    void save(Order order, List<OrderItem> orderItems);

    PageResponse<OrderDTO> selectPages(OrderPageQry pageQry);

    Order selectById(Long orderId);

    List<OrderItemDTO> listOrderItems(Long orderId);

    void updateOrderPayStatus(Order order);

    List<OrderItem> selectItemsByOrderId(Long orderId);

    int compareAndUpdateOrderStatusAndPayStatus(Long orderId, Integer sourceOrderStatus, Integer targetOrderStatus, Integer payStatus);

    int update(Order order);
}
