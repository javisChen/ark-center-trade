package com.ark.center.trade.infra.order.convertor;

import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.OrderItem;
import com.ark.center.trade.domain.order.model.vo.OrderAmount;
import com.ark.center.trade.domain.order.model.vo.OrderPay;
import com.ark.center.trade.infra.order.gateway.impl.db.OrderDO;
import com.ark.center.trade.infra.order.gateway.impl.db.OrderItemDO;
import org.springframework.stereotype.Component;

@Component
public class OrderConvertor {

    public static OrderDO toOrderDataObject(Order order) {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(order.getOrderId());
        orderDO.setTradeNo(order.getTradeNo());
        orderDO.setOrderType(order.getOrderType());
        orderDO.setOrderChannel(order.getOrderChannel());
        orderDO.setOrderStatus(order.getOrderStatus().getValue());
        orderDO.setDeliverTime(order.getDeliverTime());
        orderDO.setConfirmTime(order.getConfirmTime());
        orderDO.setBuyerRemark(order.getBuyerRemark());
        orderDO.setBuyerId(order.getBuyerId());
        orderDO.setSellerId(order.getSellerId());
        orderDO.setLogisticsCompany(order.getLogisticsCompany());
        orderDO.setLogisticsCode(order.getLogisticsCode());

        OrderAmount orderAmount = order.getOrderAmount();
        orderDO.setExpectAmount(orderAmount.getExpectAmount());
        orderDO.setActualAmount(orderAmount.getActualAmount());
        orderDO.setFreightAmount(orderAmount.getFreightAmount());

        OrderPay orderPay = order.getOrderPay();
        orderDO.setPayStatus(orderPay.getPayStatus().getValue());
        orderDO.setPayType(orderDO.getPayType());
        orderDO.setPayTradeNo(orderPay.getPayTradeNo());
        orderDO.setPayTime(orderPay.getPayTime());

        return orderDO;
    }

    public static OrderItemDO toOrderItemDataObject(Order order, OrderItem orderItem) {
        OrderItemDO orderItemDO = new OrderItemDO();
        orderItemDO.setOrderId(order.getOrderId());
        orderItemDO.setTradeNo(order.getTradeNo());
        orderItemDO.setSpuName(orderItem.getSpuName());
        orderItemDO.setSkuId(orderItem.getSkuId());
        orderItemDO.setPrice(orderItem.getPrice());
        orderItemDO.setQuantity(orderItem.getQuantity());
        orderItemDO.setExpectAmount(orderItem.getExpectAmount());
        orderItemDO.setActualAmount(orderItem.getActualAmount());
        orderItemDO.setPicUrl(orderItem.getPicUrl());
        orderItemDO.setSpecData(orderItem.getSpecData());
        return orderItemDO;
    }

}
