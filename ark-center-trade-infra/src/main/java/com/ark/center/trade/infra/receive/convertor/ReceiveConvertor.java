package com.ark.center.trade.infra.receive.convertor;

import com.ark.center.trade.client.order.command.OrderCreateReceiveCreateCmd;
import com.ark.center.trade.client.order.dto.ReceiveDTO;
import com.ark.center.trade.domain.order.model.Receive;
import com.ark.center.trade.infra.receive.gateway.db.ReceiveDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReceiveConvertor {

    Receive convertToReceive(OrderCreateReceiveCreateCmd receiveCreateCmd);

    ReceiveDO convertToReceiveDO(Receive receive);

    ReceiveDTO convertToReceiveDTO(ReceiveDO receiveDO);
}