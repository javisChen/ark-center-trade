package com.ark.center.trade.infra.order.convertor;


import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.dto.OrderProductDTO;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderConvertor {

    OrderDTO toOrderDTO(Order order);

    List<OrderItemDTO> toOrderItemDTO(List<OrderItem> orderItems);

    List<OrderProductDTO> toOrderProductDTO(List<OrderItemDTO> orderItems);
}
