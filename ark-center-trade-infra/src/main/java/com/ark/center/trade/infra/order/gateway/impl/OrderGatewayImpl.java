package com.ark.center.trade.infra.order.gateway.impl;

import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.receive.gateway.ReceiveGateway;
import com.ark.center.trade.infra.order.assembler.OrderAssembler;
import com.ark.center.trade.infra.order.convertor.OrderConvertor;
import com.ark.center.trade.infra.order.gateway.db.OrderItemMapper;
import com.ark.center.trade.infra.order.gateway.db.OrderMapper;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderGatewayImpl extends ServiceImpl<OrderMapper, Order> implements OrderGateway {

    private final OrderMapper orderMapper;

    private final OrderConvertor orderConvertor;

    private final OrderAssembler orderAssembler;

    private final OrderItemMapper orderItemMapper;

    private final ReceiveGateway receiveGateway;

    @Override
    public void save(Order order, List<OrderItem> orderItems) {

        orderMapper.insert(order);

        Long orderId = order.getId();

        String tradeNo = order.getTradeNo();

        orderItems.forEach(orderItem -> {
            orderItem.setOrderId(orderId);
            orderItem.setTradeNo(tradeNo);
            orderItemMapper.insert(orderItem);
        });

    }

    @Override
    public PageResponse<Order> selectPages(OrderQry pageQry) {
        LambdaQueryWrapper<Order> qw = Wrappers.lambdaQuery(Order.class)
                .eq(pageQry.getOrderStatus() != null, Order::getOrderStatus, pageQry.getOrderStatus())
                .orderByDesc(BaseEntity::getGmtCreate);

        IPage<Order> page = orderMapper
                .selectPage(new Page<>(pageQry.getCurrent(), pageQry.getSize()), qw);

        return PageResponse.of(page);
    }

    @Override
    public Order selectById(Long orderId) {
        return orderMapper.selectById(orderId);
    }

    @Override
    public List<OrderItemDTO> selectOrderItems(Long orderId) {
        return selectOrderItems(Lists.newArrayList(orderId));
    }

    @Override
    public List<OrderItemDTO> selectOrderItems(List<Long> orderIds) {
        LambdaQueryWrapper<OrderItem> qw = new LambdaQueryWrapper<>();
        qw.in(OrderItem::getOrderId, orderIds);
        List<OrderItem> orderItems = orderItemMapper.selectList(qw);
        return orderConvertor.toOrderItemDTO(orderItems);
    }

    @Override
    public List<ReceiveDTO> selectReceives(List<Long> orderIds) {
        return receiveGateway.selectByOrderIds(orderIds);
    }

    @Override
    public void updateOrderPayStatus(Order order) {
        Order entity = new Order();
        entity.setId(order.getId());
        entity.setPayStatus(order.getPayStatus());
        entity.setOrderStatus(order.getOrderStatus());
        orderMapper.updateById(entity);
    }

    @Override
    public List<OrderItem> selectItemsByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderItem> qw = new LambdaQueryWrapper<>();
        qw.eq(OrderItem::getOrderId, orderId);
        return orderItemMapper.selectList(qw);
    }

    @Override
    public int update(Order order) {
        return orderMapper.updateById(order);
    }

    @Override
    public int optimisticLockUpdateOrderStatusAndOthers(Order sourceOrder, Order updateOrder) {
        return orderMapper.update(updateOrder,
                new LambdaUpdateWrapper<Order>()
                        .eq(BaseEntity::getId, sourceOrder.getId())
                        .eq(Order::getOrderStatus, sourceOrder.getOrderStatus()));
    }

}

