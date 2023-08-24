package com.ark.center.trade.infra.order.gateway.impl;

import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.OrderItem;
import com.ark.center.trade.infra.order.convertor.OrderConvertor;
import com.ark.center.trade.infra.order.gateway.db.OrderDO;
import com.ark.center.trade.infra.order.gateway.db.OrderItemDO;
import com.ark.center.trade.infra.order.gateway.db.OrderItemMapper;
import com.ark.center.trade.infra.order.gateway.db.OrderMapper;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {

    private final OrderMapper orderMapper;

    private final OrderConvertor orderConvertor;

    private final OrderItemMapper orderItemMapper;

    @Override
    public void save(Order order) {

        OrderDO orderDO = orderConvertor.toOrderDO(order);

        // 保存订单
        orderMapper.insert(orderDO);

        // 保存订单项
        List<OrderItem> orderItemList = order.getOrderItemList();
        orderItemList.stream()
                .map(orderItem -> orderConvertor.toOrderItemDO(orderDO, orderItem))
                .forEach(orderItemMapper::insert);

        order.setOrderId(orderDO.getId());
    }

    @Override
    public PageResponse<OrderDTO> getPageList(OrderPageQry pageQry) {
        LambdaQueryWrapper<OrderDO> qw = Wrappers.lambdaQuery(OrderDO.class)
                .orderByDesc(BaseEntity::getGmtCreate);

        IPage<OrderDTO> page = orderMapper
                .selectPage(new Page<>(pageQry.getCurrent(), pageQry.getSize()), qw)
                .convert(orderConvertor::toOrderDTO);

        return PageResponse.of(page);
    }

    @Override
    public Order findById(Long orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        return orderConvertor.toOrderDomainObject(orderDO);
    }

    @Override
    public List<OrderItemDTO> listOrderItems(Long orderId) {
        LambdaQueryWrapper<OrderItemDO> qw = new LambdaQueryWrapper<>();
        qw.eq(OrderItemDO::getOrderId, orderId);
        List<OrderItemDO> orderItemDOS = orderItemMapper.selectList(qw);
        return orderConvertor.toOrderItemDTO(orderItemDOS);
    }

    @Override
    public void updateOrderPayStatus(Order order) {
        OrderDO entity = new OrderDO();
        entity.setId(order.getOrderId());
        entity.setPayStatus(order.getOrderPay().getPayStatus().getValue());
        entity.setOrderStatus(order.getOrderStatus().getValue());
        orderMapper.updateById(entity);
    }

    @Override
    public List<OrderItem> findItemsByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderItemDO> qw = new LambdaQueryWrapper<>();
        qw.eq(OrderItemDO::getOrderId, orderId);
        List<OrderItemDO> orderItemDOS = orderItemMapper.selectList(qw);
        return orderConvertor.toOrderItemDomainObject(orderItemDOS);
    }

}
