package com.ark.center.trade.infra.order.assembler;


import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.dto.OrderProductDTO;
import com.ark.center.trade.infra.order.Order;
import com.ark.center.trade.infra.order.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderAssembler {

    OrderDTO toOrderDTO(Order order);

    List<OrderItemDTO> toOrderItemDTO(List<OrderItem> orderItems);

    List<OrderProductDTO> toOrderProductDTO(List<OrderItemDTO> orderItems);
}
