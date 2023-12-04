package com.ark.center.trade.infra.receive.gateway.impl;

import com.ark.center.trade.client.order.dto.OrderReceiveDTO;
import com.ark.center.trade.domain.order.OrderReceive;
import com.ark.center.trade.domain.receive.gateway.OrderReceiveGateway;
import com.ark.center.trade.infra.order.assembler.OrderReceiveAssembler;
import com.ark.center.trade.infra.receive.gateway.db.OrderReceiveMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderReceiveGatewayImpl implements OrderReceiveGateway {

    private final OrderReceiveMapper OrderReceiveMapper;
    private final OrderReceiveAssembler orderReceiveAssembler;

    @Override
    public void save(OrderReceive orderReceive) {
        OrderReceive OrderReceiveDO = orderReceiveAssembler.convertToReceiveDO(orderReceive);
        OrderReceiveMapper.insert(OrderReceiveDO);
    }

    @Override
    public List<OrderReceiveDTO> selectByOrderIds(List<Long> orderIds) {
        LambdaQueryWrapper<OrderReceive> qw = new LambdaQueryWrapper<>();
        qw.in(OrderReceive::getOrderId, orderIds);
        List<OrderReceive> OrderReceive = OrderReceiveMapper.selectList(qw);
        return orderReceiveAssembler.convertToReceiveDTO(OrderReceive);
    }

}
