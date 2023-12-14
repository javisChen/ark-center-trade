package com.ark.center.trade.domain.order.gateway;

import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.dto.OrderReceiveDTO;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface OrderGateway {

    void save(Order order, List<OrderItem> orderItems);

    IPage<Order> selectPages(OrderQry pageQry);

    Order selectById(Long orderId);

    List<OrderItemDTO> selectOrderItems(Long orderId);

    List<OrderItemDTO> selectOrderItems(List<Long> orderIds);

    List<OrderReceiveDTO> selectReceives(List<Long> orderIds);

    void updateOrderPayStatus(Order order);

    List<OrderItem> selectItemsByOrderId(Long orderId);

    boolean update(Order order);

    /**
     * 使用订单id+订单状态作为乐观锁更新订单状态
     *
     * @param sourceOrder 条件
     * @param updateOrder 更新值
     * @return 更新影响行数
     */
    boolean optimisticLockUpdateOrderStatusAndOthers(Order sourceOrder, Order updateOrder);

    Order selectByTradeNo(String tradeNo);
}
