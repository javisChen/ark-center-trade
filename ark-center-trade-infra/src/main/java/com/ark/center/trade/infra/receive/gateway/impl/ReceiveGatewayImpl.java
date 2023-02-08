package com.ark.center.trade.infra.receive.gateway.impl;

import com.ark.center.trade.client.order.dto.ReceiveDTO;
import com.ark.center.trade.domain.order.gateway.ReceiveGateway;
import com.ark.center.trade.domain.order.model.Receive;
import com.ark.center.trade.infra.receive.convertor.ReceiveConvertor;
import com.ark.center.trade.infra.receive.gateway.db.ReceiveDO;
import com.ark.center.trade.infra.receive.gateway.db.ReceiveMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiveGatewayImpl implements ReceiveGateway {

    private final ReceiveMapper receiveMapper;
    private final ReceiveConvertor receiveConvertor;

    @Override
    public void save(Receive receive) {
        ReceiveDO receiveDO = receiveConvertor.convertToReceiveDO(receive);
        receiveMapper.insert(receiveDO);
    }

    @Override
    public ReceiveDTO findByOrderId(Long orderId) {
        LambdaQueryWrapper<ReceiveDO> qw = new LambdaQueryWrapper<>();
        qw.eq(ReceiveDO::getOrderId, orderId)
                        .last("limit 1");
        ReceiveDO receiveDO = receiveMapper.selectOne(qw);
        return receiveConvertor.convertToReceiveDTO(receiveDO);
    }

}
