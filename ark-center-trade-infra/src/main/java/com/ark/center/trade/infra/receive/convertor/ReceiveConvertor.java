package com.ark.center.trade.infra.receive.convertor;

import com.ark.center.trade.client.order.command.OrderCreateReceiveCreateCmd;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.domain.receive.Receive;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReceiveConvertor {

    com.ark.center.trade.domain.order.model.Receive convertToReceive(OrderCreateReceiveCreateCmd receiveCreateCmd);

    Receive convertToReceiveDO(com.ark.center.trade.domain.order.model.Receive receive);

    ReceiveDTO convertToReceiveDTO(Receive receive);

    List<ReceiveDTO> convertToReceiveDTO(List<Receive> receive);
}