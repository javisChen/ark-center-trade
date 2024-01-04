package com.ark.center.trade.infra.order.gateway.impl;

import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.dto.OrderReceiveDTO;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.receive.gateway.OrderReceiveGateway;
import com.ark.center.trade.infra.order.assembler.OrderAssembler;
import com.ark.center.trade.infra.order.gateway.db.OrderItemMapper;
import com.ark.center.trade.infra.order.gateway.db.OrderMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderGatewayImpl extends ServiceImpl<OrderMapper, Order> implements OrderGateway {

    private final OrderAssembler orderAssembler;

    private final OrderItemMapper orderItemMapper;

    private final OrderReceiveGateway orderReceiveGateway;

    @Override
    public void save(Order order, List<OrderItem> orderItems) {

        save(order);

        Long orderId = order.getId();

        String tradeNo = order.getTradeNo();

        orderItems.forEach(orderItem -> {
            orderItem.setOrderId(orderId);
            orderItem.setTradeNo(tradeNo);
            orderItemMapper.insert(orderItem);
        });

    }

    @Override
    public IPage<Order> selectPages(OrderQry pageQry) {
        LambdaQueryWrapper<Order> qw = Wrappers.lambdaQuery(Order.class)
                .like(StringUtils.isNotBlank(pageQry.getTradeNo()), Order::getTradeNo, pageQry.getTradeNo())
                .eq(pageQry.getOrderStatus() != null, Order::getOrderStatus, pageQry.getOrderStatus())
                .orderByDesc(BaseEntity::getCreateTime);

        return this.page(new Page<>(pageQry.getCurrent(), pageQry.getSize()), qw);
    }

    @Override
    public Order selectById(Long orderId) {
        return getById(orderId);
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
        return orderAssembler.toOrderItemDTO(orderItems);
    }

    @Override
    public List<OrderReceiveDTO> selectReceives(List<Long> orderIds) {
        return orderReceiveGateway.selectByOrderIds(orderIds);
    }

    @Override
    public void updateOrderPayStatus(Order order) {
        Order entity = new Order();
        entity.setId(order.getId());
        entity.setPayStatus(order.getPayStatus());
        entity.setOrderStatus(order.getOrderStatus());
        updateById(entity);
    }

    @Override
    public List<OrderItem> selectItemsByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderItem> qw = new LambdaQueryWrapper<>();
        qw.eq(OrderItem::getOrderId, orderId);
        return orderItemMapper.selectList(qw);
    }

    @Override
    public boolean update(Order order) {
        return updateById(order);
    }

    @Override
    public boolean optimisticLockUpdateOrderStatusAndOthers(Order sourceOrder, Order updateOrder) {
        return update(updateOrder,
                new LambdaUpdateWrapper<Order>()
                        .eq(BaseEntity::getId, sourceOrder.getId())
                        .eq(Order::getOrderStatus, sourceOrder.getOrderStatus()));
    }

    @Override
    public Order selectByTradeNo(String tradeNo) {
        return lambdaQuery()
                .eq(Order::getTradeNo, tradeNo)
                .last("limit 1")
                .one();
    }

}

