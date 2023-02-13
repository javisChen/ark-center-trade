package com.ark.center.trade.application.order.assembler;

import com.ark.center.trade.client.order.dto.ReceiveDTO;
import com.ark.center.trade.client.order.dto.info.OrderBaseDTO;
import com.ark.center.trade.client.order.dto.info.OrderChargeDTO;
import com.ark.center.trade.client.order.dto.info.OrderCommodityDTO;
import com.ark.center.trade.client.order.dto.info.OrderInfoDTO;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.OrderItem;
import com.ark.center.trade.domain.order.model.vo.OrderAmount;
import com.ark.center.trade.domain.order.model.vo.OrderPay;
import com.ark.center.trade.infra.order.convertor.OrderConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderAssembler {

    private final OrderConvertor orderConvertor;

    public OrderInfoDTO assemble(Order order, List<OrderItem> orderItems, ReceiveDTO receiveDTO) {
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        // 订单基本信息
        OrderBaseDTO orderBaseDTO = assembleOrderBase(order);
        orderInfoDTO.setOrderBase(orderBaseDTO);

        // 订单费用信息
        OrderChargeDTO orderChargeDTO = assembleOrderCharge(order);
        orderInfoDTO.setOrderCharge(orderChargeDTO);

        // 订单收货信息
        orderInfoDTO.setOrderReceive(receiveDTO);

        // 订单商品信息
        List<OrderCommodityDTO> orderCommodityDTO = assembleOrderCommodity(orderItems);
        orderInfoDTO.setOrderCommodities(orderCommodityDTO);
        return orderInfoDTO;
    }

    private List<OrderCommodityDTO> assembleOrderCommodity(List<OrderItem> orderItems) {
        return orderConvertor.toOrderCommodityDTO(orderItems);
    }

    private OrderChargeDTO assembleOrderCharge(Order order) {
        OrderChargeDTO orderChargeDTO = new OrderChargeDTO();
        OrderAmount orderAmount = order.getOrderAmount();
        orderChargeDTO.setExpectAmount(orderAmount.getExpectAmount());
        orderChargeDTO.setActualAmount(orderAmount.getActualAmount());
        orderChargeDTO.setFreightAmount(orderAmount.getFreightAmount());
        return orderChargeDTO;
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
        OrderPay.PayType payType = orderPay.getPayType();
        if (payType != null) {
            orderBaseDTO.setPayType(payType.getValue());
        }
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

}
