package com.ark.center.trade.application.order.assembler;

import com.ark.center.trade.client.order.dto.ReceiveDTO;
import com.ark.center.trade.client.order.dto.info.OrderBaseDTO;
import com.ark.center.trade.client.order.dto.info.OrderChargeDTO;
import com.ark.center.trade.client.order.dto.info.OrderProductDTO;
import com.ark.center.trade.client.order.dto.info.OrderDetailsDTO;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.ark.center.trade.infra.order.convertor.OrderConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderAssembler {

    private final OrderConvertor orderConvertor;

    public OrderDetailsDTO assemble(Order order, List<OrderItem> orderItems, ReceiveDTO receiveDTO) {
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        // 订单基本信息
        OrderBaseDTO orderBaseDTO = assembleOrderBase(order);
        orderDetailsDTO.setOrderBase(orderBaseDTO);

        // 订单费用信息
        OrderChargeDTO orderChargeDTO = assembleOrderCharge(order);
        orderDetailsDTO.setOrderCharge(orderChargeDTO);

        // 订单收货信息
        orderDetailsDTO.setOrderReceive(receiveDTO);

        // 订单商品信息
        List<OrderProductDTO> orderProductDTO = assembleOrderProducts(orderItems);
        orderDetailsDTO.setOrderProducts(orderProductDTO);
        return orderDetailsDTO;
    }

    private List<OrderProductDTO> assembleOrderProducts(List<OrderItem> orderItems) {
        return orderConvertor.toOrderProductDTO(orderItems);
    }

    private OrderChargeDTO assembleOrderCharge(Order order) {
        OrderChargeDTO orderChargeDTO = new OrderChargeDTO();
        orderChargeDTO.setExpectAmount(order.getExpectAmount());
        orderChargeDTO.setActualAmount(order.getActualAmount());
        orderChargeDTO.setFreightAmount(order.getFreightAmount());
        return orderChargeDTO;
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
        orderBaseDTO.setPayType(order.getPayType());
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

}
