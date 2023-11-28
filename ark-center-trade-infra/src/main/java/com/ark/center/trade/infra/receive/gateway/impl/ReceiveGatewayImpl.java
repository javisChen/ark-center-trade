package com.ark.center.trade.infra.receive.gateway.impl;

import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.domain.receive.gateway.ReceiveGateway;
import com.ark.center.trade.domain.receive.Receive;
import com.ark.center.trade.infra.receive.convertor.ReceiveConvertor;
import com.ark.center.trade.infra.receive.gateway.db.ReceiveMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReceiveGatewayImpl implements ReceiveGateway {

    private final ReceiveMapper receiveMapper;
    private final ReceiveConvertor receiveConvertor;

    @Override
    public void save(com.ark.center.trade.domain.order.model.Receive receive) {
        Receive receiveDO = receiveConvertor.convertToReceiveDO(receive);
        receiveMapper.insert(receiveDO);
    }

    @Override
    public ReceiveDTO selectByOrderId(Long orderId) {
        LambdaQueryWrapper<Receive> qw = new LambdaQueryWrapper<>();
        qw.eq(Receive::getOrderId, orderId)
                .last("limit 1");
        Receive receive = receiveMapper.selectOne(qw);
        return receiveConvertor.convertToReceiveDTO(receive);
    }

    @Override
    public List<ReceiveDTO> selectByOrderIds(List<Long> orderIds) {
        LambdaQueryWrapper<Receive> qw = new LambdaQueryWrapper<>();
        qw.in(Receive::getOrderId, orderIds);
        List<Receive> receive = receiveMapper.selectList(qw);
        return receiveConvertor.convertToReceiveDTO(receive);
    }

}
