package com.ark.center.trade.infra.receive.assembler;

import com.ark.center.trade.client.order.command.OrderCreateReceiveCreateCmd;
import com.ark.center.trade.client.receive.cmd.ReceiveCmd;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.infra.order.OrderReceive;
import com.ark.center.trade.infra.receive.UserReceive;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserReceiveAssembler {

    UserReceive toReceive(ReceiveCmd receiveCmd);

    UserReceive convertToReceive(OrderCreateReceiveCreateCmd receiveCreateCmd);

    UserReceive convertToReceiveDO(OrderReceive orderReceive);

    ReceiveDTO convertToReceiveDTO(UserReceive userReceive);

    List<ReceiveDTO> convertToReceiveDTO(List<UserReceive> userReceive);
}