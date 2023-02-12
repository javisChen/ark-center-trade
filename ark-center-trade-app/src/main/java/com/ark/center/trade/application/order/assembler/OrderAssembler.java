package com.ark.center.trade.application.order.assembler;

import com.ark.center.trade.client.order.dto.ReceiveDTO;
import com.ark.center.trade.client.order.dto.info.OrderBaseDTO;
import com.ark.center.trade.client.order.dto.info.OrderInfoDTO;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.vo.OrderPay;
import org.springframework.stereotype.Component;

@Component
public class OrderAssembler {

    public OrderInfoDTO assemble(Order order, ReceiveDTO receiveDTO) {
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        OrderBaseDTO orderBaseDTO = assembleOrderBase(order);

        orderInfoDTO.setOrderBaseDTO(orderBaseDTO);
        orderInfoDTO.setOrderChargeDTO();
        orderInfoDTO.setReceiveDTO();
        orderInfoDTO.setOrderCommodityDTO();
        return orderInfoDTO;
    }

    private OrderBaseDTO assembleOrderBase(Order order) {
        OrderBaseDTO orderBaseDTO = new OrderBaseDTO();
        orderBaseDTO.setId(order.getOrderId());
        orderBaseDTO.setTradeNo(order.getTradeNo());
        orderBaseDTO.setOrderType(order.getOrderType());
        orderBaseDTO.setOrderChannel(order.getOrderChannel());
        orderBaseDTO.setOrderStatus(order.getOrderStatus().getValue());
        OrderPay orderPay = order.getOrderPay();
        orderBaseDTO.setPayStatus(orderPay.getPayStatus().getValue());
        orderBaseDTO.setPayType(orderPay.getPayType().getValue());
        orderBaseDTO.setPayTradeNo(orderPay.getPayTradeNo());
        orderBaseDTO.setPayTime(orderPay.getPayTime());
        orderBaseDTO.setDeliverTime(order.getDeliverTime());
        orderBaseDTO.setConfirmTime(order.getConfirmTime());
        orderBaseDTO.setBuyerRemark(order.getBuyerRemark());
        orderBaseDTO.setBuyerId(order.getBuyerId());
        orderBaseDTO.setSellerId(order.getSellerId());
        orderBaseDTO.setLogisticsCompany(order.getLogisticsCompany());
        orderBaseDTO.setLogisticsCode(order.getLogisticsCode());
        return orderBaseDTO;
    }

    ;
}
