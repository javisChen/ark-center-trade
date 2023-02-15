package com.ark.center.trade.infra.order.convertor;

import com.alibaba.fastjson.JSON;
import com.ark.center.trade.client.order.command.OrderCreateItemCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.dto.info.OrderCommodityDTO;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.OrderItem;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.domain.order.model.SkuAttr;
import com.ark.center.trade.domain.order.model.vo.OrderAmount;
import com.ark.center.trade.domain.order.model.vo.OrderPay;
import com.ark.center.trade.domain.order.model.vo.OrderPay.PayStatus;
import com.ark.center.trade.domain.order.model.vo.OrderPay.PayType;
import com.ark.center.trade.infra.order.gateway.db.OrderDO;
import com.ark.center.trade.infra.order.gateway.db.OrderItemDO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderConvertor {

    default OrderDO toOrderDO(Order order) {
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

    default OrderItemDO toOrderItemDO(OrderDO order, OrderItem orderItem) {
        OrderItemDO orderItemDO = new OrderItemDO();
        orderItemDO.setOrderId(order.getId());
        orderItemDO.setTradeNo(order.getTradeNo());
        orderItemDO.setSpuName(orderItem.getSpuName());
        orderItemDO.setSkuId(orderItem.getSkuId());
        orderItemDO.setPrice(orderItem.getPrice());
        orderItemDO.setQuantity(orderItem.getQuantity());
        orderItemDO.setExpectAmount(orderItem.getExpectAmount());
        orderItemDO.setActualAmount(orderItem.getActualAmount());
        orderItemDO.setPicUrl(orderItem.getPicUrl());
        orderItemDO.setSpecData(JSON.toJSONString(orderItem.getSpecData()));
        return orderItemDO;
    }

    default OrderItem toOrderItemDomainObject(OrderCreateItemCmd orderCreateItemCmd, Sku sku) {
        // 订单项价格 = 销售价 * 数量
        int totalAmount = sku.getSalesPrice() * orderCreateItemCmd.getQuantity();
        OrderItem orderItem = new OrderItem();
        orderItem.setSpuName(sku.getSpuName());
        orderItem.setSkuId(orderCreateItemCmd.getSkuId());
        orderItem.setPrice(sku.getSalesPrice());
        orderItem.setQuantity(orderCreateItemCmd.getQuantity());
        orderItem.setExpectAmount(totalAmount);
        orderItem.setActualAmount(totalAmount);
        orderItem.setPicUrl(sku.getMainPicture());
        List<SkuAttr> specList = sku.getSpecList();
        orderItem.setSpecData(buildSpecString(specList));
        return orderItem;
    }

    private String buildSpecString(List<SkuAttr> specList) {
        StringBuilder builder = new StringBuilder();
        for (SkuAttr skuAttr : specList) {
            builder.append(skuAttr.getAttrName()).append("：").append(skuAttr.getAttrValue()).append(";");
        }
        return builder.toString();
    }

    List<OrderItem> toOrderItemDomainObject(List<OrderItemDO> orderItemDOS);

    default OrderDTO toOrderDTO(OrderDO orderDO) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderDO.getId());
        orderDTO.setTradeNo(orderDO.getTradeNo());
        orderDTO.setOrderType(orderDO.getOrderType());
        orderDTO.setOrderChannel(orderDO.getOrderChannel());
        orderDTO.setOrderStatus(orderDO.getOrderStatus());
        orderDTO.setPayStatus(orderDO.getPayStatus());
        orderDTO.setPayType(orderDO.getPayType());
        orderDTO.setExpectAmount(orderDO.getExpectAmount());
        orderDTO.setActualAmount(orderDO.getActualAmount());
        orderDTO.setFreightAmount(orderDO.getFreightAmount());
        orderDTO.setPayTradeNo(orderDTO.getPayTradeNo());
        orderDTO.setPayTime(orderDO.getPayTime());
        orderDTO.setDeliverTime(orderDO.getDeliverTime());
        orderDTO.setConfirmTime(orderDO.getConfirmTime());
        orderDTO.setBuyerRemark(orderDO.getBuyerRemark());
        orderDTO.setBuyerId(orderDO.getBuyerId());
        orderDTO.setSellerId(orderDO.getSellerId());
        orderDTO.setLogisticsCompany(orderDO.getLogisticsCompany());
        orderDTO.setLogisticsCode(orderDO.getLogisticsCode());
        orderDTO.setGmtCreate(orderDO.getGmtCreate());
        return orderDTO;
    }


    default Order toOrderDomainObject(OrderDO orderDO) {
        Order order = new Order();
        order.setOrderId(orderDO.getId());
        order.setTradeNo(orderDO.getTradeNo());
        order.setOrderType(orderDO.getOrderType());
        order.setOrderChannel(orderDO.getOrderChannel());
        order.setOrderStatus(Order.OrderStatus.getByValue(orderDO.getOrderStatus()));
        order.setDeliverTime(orderDO.getDeliverTime());
        order.setConfirmTime(orderDO.getConfirmTime());
        order.setBuyerRemark(orderDO.getBuyerRemark());
        order.setBuyerId(orderDO.getBuyerId());
        order.setSellerId(orderDO.getSellerId());
        order.setLogisticsCompany(orderDO.getLogisticsCompany());
        order.setLogisticsCode(orderDO.getLogisticsCode());

        OrderAmount orderAmount = new OrderAmount();
        orderAmount.setExpectAmount(orderDO.getExpectAmount());
        orderAmount.setActualAmount(orderDO.getActualAmount());
        orderAmount.setFreightAmount(orderDO.getFreightAmount());
        order.setOrderAmount(orderAmount);

        OrderPay orderPay = new OrderPay();
        orderPay.setPayStatus(PayStatus.getByValue(orderDO.getPayStatus()));
        orderPay.setPayType(PayType.getByValue(orderDO.getPayType()));
        orderPay.setPayTradeNo(orderPay.getPayTradeNo());
        orderPay.setPayTime(orderPay.getPayTime());
        order.setOrderPay(orderPay);
        return order;

    }

    List<OrderItemDTO> toOrderItemDTO(List<OrderItemDO> orderItemDOS);

    List<OrderCommodityDTO> toOrderCommodityDTO(List<OrderItem> orderItems);
}
