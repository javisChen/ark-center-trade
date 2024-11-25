package com.ark.center.trade.infra.order.service;

import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.dto.OrderReceiveDTO;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.infra.order.Order;
import com.ark.center.trade.infra.order.OrderItem;
import com.ark.center.trade.infra.order.assembler.OrderAssembler;
import com.ark.center.trade.infra.order.db.OrderItemMapper;
import com.ark.center.trade.infra.order.db.OrderMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    private final OrderAssembler orderAssembler;

    private final OrderItemMapper orderItemMapper;

    private final OrderReceiveService orderReceiveService;

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

    public IPage<Order> selectPages(OrderQry pageQry) {
        LambdaQueryWrapper<Order> qw = Wrappers.lambdaQuery(Order.class)
                .like(StringUtils.isNotBlank(pageQry.getTradeNo()), Order::getTradeNo, pageQry.getTradeNo())
                .eq(pageQry.getOrderStatus() != null, Order::getOrderStatus, pageQry.getOrderStatus())
                .orderByDesc(BaseEntity::getCreateTime);
        return this.page(new Page<>(pageQry.getCurrent(), pageQry.getSize()), qw);
    }

    public Order byId(Long orderId) {
        return getById(orderId);
    }

    public List<OrderItemDTO> selectOrderItems(Long orderId) {
        return selectOrderItems(Lists.newArrayList(orderId));
    }

    public List<OrderItemDTO> selectOrderItems(List<Long> orderIds) {
        LambdaQueryWrapper<OrderItem> qw = new LambdaQueryWrapper<>();
        qw.in(OrderItem::getOrderId, orderIds);
        List<OrderItem> orderItems = orderItemMapper.selectList(qw);
        return orderAssembler.toOrderItemDTO(orderItems);
    }

    public List<OrderReceiveDTO> selectReceives(List<Long> orderIds) {
        return orderReceiveService.selectByOrderIds(orderIds);
    }

    public void updateOrderPayStatus(Order order) {
        Order entity = new Order();
        entity.setId(order.getId());
        entity.setPayStatus(order.getPayStatus());
        entity.setOrderStatus(order.getOrderStatus());
        updateById(entity);
    }

    public List<OrderItem> selectItemsByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderItem> qw = new LambdaQueryWrapper<>();
        qw.eq(OrderItem::getOrderId, orderId);
        return orderItemMapper.selectList(qw);
    }

    public boolean update(Order order) {
        return updateById(order);
    }

    public boolean optimisticLockUpdateOrderStatusAndOthers(Order sourceOrder, Order updateOrder) {
        return lambdaUpdate()
                .set(Order::getOrderStatus, updateOrder.getOrderStatus())
                .eq(BaseEntity::getId, sourceOrder.getId())
                .eq(Order::getOrderStatus, sourceOrder.getOrderStatus())
                .update();
    }

    public Order byTradeNo(String tradeNo) {
        if (StringUtils.isBlank(tradeNo)) {
            log.warn("TradeNo cannot be blank");
            return null;
        }
        Order order = lambdaQuery()
                .eq(Order::getTradeNo, tradeNo)
                .last("limit 1")
                .one();
        if (order == null) {
            log.warn("Order {} does not exist", tradeNo);
        }
        return order;
    }

}

