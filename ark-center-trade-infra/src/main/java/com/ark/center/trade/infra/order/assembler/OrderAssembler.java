package com.ark.center.trade.infra.order.assembler;

import com.ark.center.trade.client.order.dto.*;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.infra.order.convertor.OrderConvertor;
import com.ark.component.common.util.assemble.FieldsAssembler;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderAssembler {

    private final OrderConvertor orderConvertor;
    private final OrderGateway orderGateway;

    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        // 订单基本信息
        OrderBaseDTO orderBaseDTO = assembleOrderBase(order);
        orderDTO.setOrderBase(orderBaseDTO);

        // 订单费用信息
        OrderAmountDTO orderAmountDTO = assembleOrderCharge(order);
        orderDTO.setOrderAmount(orderAmountDTO);
        return orderDTO;
    }

    private List<OrderProductDTO> toOrderProducts(List<OrderItemDTO> orderItems) {
        return orderConvertor.toOrderProductDTO(orderItems);
    }

    private OrderAmountDTO assembleOrderCharge(Order order) {
        OrderAmountDTO orderAmountDTO = new OrderAmountDTO();
        orderAmountDTO.setExpectAmount(order.getExpectAmount());
        orderAmountDTO.setActualAmount(order.getActualAmount());
        orderAmountDTO.setFreightAmount(order.getFreightAmount());
        return orderAmountDTO;
    }

    private OrderBaseDTO assembleOrderBase(Order order) {
        OrderBaseDTO orderBaseDTO = new OrderBaseDTO();
        orderBaseDTO.setCreateTime(order.getGmtCreate());
        orderBaseDTO.setId(order.getId());
        orderBaseDTO.setTradeNo(order.getTradeNo());
        orderBaseDTO.setOrderType(order.getOrderType());
        orderBaseDTO.setOrderChannel(order.getOrderChannel());
        orderBaseDTO.setOrderStatus(order.getOrderStatus());
        orderBaseDTO.setPayStatus(order.getPayStatus());
        orderBaseDTO.setPayTypeCode(order.getPayTypeCode());
        orderBaseDTO.setPayTradeNo(order.getPayTradeNo());
        orderBaseDTO.setPayTime(order.getPayTime());
        orderBaseDTO.setDeliverTime(order.getDeliverTime());
        orderBaseDTO.setConfirmTime(order.getConfirmTime());
        orderBaseDTO.setBuyerRemark(order.getBuyerRemark());
        orderBaseDTO.setBuyerId(order.getBuyerId());
        orderBaseDTO.setSellerId(order.getSellerId());
        orderBaseDTO.setLogisticsCompany(order.getLogisticsCompany());
        orderBaseDTO.setLogisticsCode(order.getLogisticsCode());
        return orderBaseDTO;
    }

    public OrderDTO assemble(Order record, OrderAssembleProfiles profiles) {
        return assemble(Lists.newArrayList(record), profiles).get(0);
    }

        public List<OrderDTO> assemble(List<Order> records, OrderAssembleProfiles profiles) {
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }
        log.info("Assembling orders, profiles = {}", profiles);
        List<OrderDTO> orders = records.stream().map(this::toOrderDTO).toList();

        Function<OrderDTO, Long> orderIdFunc = orderDTO -> orderDTO.getOrderBase().getId();

        FieldsAssembler.execute(profiles.getWithReceive(),
                orders,
                orderIdFunc,
                (orderDTO, receiveDTO) -> orderDTO.setOrderReceive(receiveDTO.get(0)),
                orderGateway::selectReceives,
                ReceiveDTO::getOrderId);

        FieldsAssembler.execute(profiles.getWithOrderItems(),
                orders,
                orderIdFunc,
                (orderDTO, ois) -> orderDTO.setOrderProducts(toOrderProducts(ois)),
                orderGateway::selectOrderItems,
                OrderItemDTO::getOrderId);

        return orders;
    }
}
