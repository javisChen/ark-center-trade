package com.ark.center.trade.infra.order.convertor;


import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.dto.info.OrderProductDTO;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderConvertor {

    default OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTradeNo(order.getTradeNo());
        orderDTO.setOrderType(order.getOrderType());
        orderDTO.setOrderChannel(order.getOrderChannel());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setPayStatus(order.getPayStatus());
        orderDTO.setPayType(order.getPayType());
        orderDTO.setExpectAmount(order.getExpectAmount());
        orderDTO.setActualAmount(order.getActualAmount());
        orderDTO.setFreightAmount(order.getFreightAmount());
        orderDTO.setPayTradeNo(orderDTO.getPayTradeNo());
        orderDTO.setPayTime(order.getPayTime());
        orderDTO.setDeliverTime(order.getDeliverTime());
        orderDTO.setConfirmTime(order.getConfirmTime());
        orderDTO.setBuyerRemark(order.getBuyerRemark());
        orderDTO.setBuyerId(order.getBuyerId());
        orderDTO.setSellerId(order.getSellerId());
        orderDTO.setLogisticsCompany(order.getLogisticsCompany());
        orderDTO.setLogisticsCode(order.getLogisticsCode());
        orderDTO.setGmtCreate(order.getGmtCreate());
        return orderDTO;
    }


    List<OrderItemDTO> toOrderItemDTO(List<OrderItem> orderItems);

    List<OrderProductDTO> toOrderProductDTO(List<OrderItem> orderItems);
}
