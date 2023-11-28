package com.ark.center.trade.domain.order.gateway;

import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.ark.component.dto.PageResponse;

import java.util.List;

public interface OrderGateway {

    void save(Order order, List<OrderItem> orderItems);

    PageResponse<Order> selectPages(OrderQry pageQry);

    Order selectById(Long orderId);

    List<OrderItemDTO> selectOrderItems(Long orderId);

    List<OrderItemDTO> selectOrderItems(List<Long> orderIds);

    List<ReceiveDTO> selectReceives(List<Long> orderIds);

    void updateOrderPayStatus(Order order);

    List<OrderItem> selectItemsByOrderId(Long orderId);

    int update(Order order);

    /**
     * 使用订单id+订单状态作为乐观锁更新订单状态
     * @param sourceOrder 条件
     * @param updateOrder 更新值
     * @return 更新影响行数
     */
    int optimisticLockUpdateOrderStatusAndOthers(Order sourceOrder, Order updateOrder);
}
