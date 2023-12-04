package com.ark.center.trade.infra.order.assembler;

import com.ark.center.trade.client.order.command.OrderCreateReceiveCreateCmd;
import com.ark.center.trade.client.order.dto.OrderReceiveDTO;
import com.ark.center.trade.domain.order.OrderReceive;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderReceiveAssembler {

    OrderReceive convertToReceive(OrderCreateReceiveCreateCmd receiveCreateCmd);

    OrderReceive convertToReceiveDO(OrderReceive orderReceive);

    OrderReceiveDTO convertToReceiveDTO(OrderReceive orderReceive);

    List<OrderReceiveDTO> convertToReceiveDTO(List<OrderReceive> orderReceives);
}