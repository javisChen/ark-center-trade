package com.ark.center.trade.infra.order.service;

import com.ark.center.trade.client.order.dto.OrderReceiveDTO;
import com.ark.center.trade.infra.order.OrderReceive;
import com.ark.center.trade.infra.order.assembler.OrderReceiveAssembler;
import com.ark.center.trade.infra.order.db.OrderReceiveMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderReceiveService extends ServiceImpl<OrderReceiveMapper, OrderReceive> {

    private final OrderReceiveMapper OrderReceiveMapper;
    private final OrderReceiveAssembler orderReceiveAssembler;

//
//    public void save(OrderReceive orderReceive) {
////        OrderReceive OrderReceiveDO = orderReceiveAssembler.convertToReceiveDO(orderReceive);
//        OrderReceiveMapper.insert(orderReceive);
//    }

    public List<OrderReceiveDTO> selectByOrderIds(List<Long> orderIds) {
        LambdaQueryWrapper<OrderReceive> qw = new LambdaQueryWrapper<>();
        qw.in(OrderReceive::getOrderId, orderIds);
        List<OrderReceive> OrderReceive = OrderReceiveMapper.selectList(qw);
        return orderReceiveAssembler.convertToReceiveDTO(OrderReceive);
    }

}
