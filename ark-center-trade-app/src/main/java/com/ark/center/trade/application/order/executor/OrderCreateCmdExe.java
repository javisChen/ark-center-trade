package com.ark.center.trade.application.order.executor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderCreateItemCmd;
import com.ark.center.trade.client.order.command.OrderCreateReceiveCreateCmd;
import com.ark.center.trade.domain.order.OrderStatus;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.order.gateway.ReceiveGateway;
import com.ark.center.trade.domain.order.gateway.SkuGateway;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.OrderItem;
import com.ark.center.trade.domain.order.model.Receive;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.domain.order.model.vo.OrderAmount;
import com.ark.center.trade.domain.order.model.vo.OrderPay;
import com.ark.center.trade.infra.order.convertor.OrderConvertor;
import com.ark.center.trade.infra.order.stm.TradeOrderStateMachine;
import com.ark.center.trade.infra.receive.convertor.ReceiveConvertor;
import com.ark.component.common.ParamsChecker;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.exception.ExceptionFactory;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderCreateCmdExe {
    private final OrderGateway orderGateway;

    private final SkuGateway skuGateway;

    private final ReceiveGateway receiveGateway;

    private final ReceiveConvertor receiveConvertor;

    private final OrderConvertor orderConvertor;

    private final TradeOrderStateMachine tradeOrderStateMachine;

    public Long execute(OrderCreateCmd orderCreateCmd) {
        // 生成工单号
        String tradeNo = IdUtil.getSnowflakeNextIdStr();
        // 组装订单数据
        Order order = assembleOrder(orderCreateCmd, tradeNo);
        // 保存订单信息
        Long orderId = saveOrder(order);
        // 保存收货信息
        saveReceive(orderCreateCmd, orderId);
        // //
        // tradeOrderStateMachine.create(String.valueOf(orderId), orderCreateCmd);
        return orderId;
    }

    private List<OrderItem> assembleOrderItems(OrderCreateCmd orderCreateCmd) {
        List<OrderCreateItemCmd> orderItems = orderCreateCmd.getOrderItems();
        List<Long> skuIds = CollUtil.map(orderItems, OrderCreateItemCmd::getSkuId, true);
        Map<Long, Sku> skuMap = requestSkuList(skuIds);
        List<OrderItem> orderItemList = Lists.newArrayList();
        for (OrderCreateItemCmd itemCmd :orderItems) {
            Sku sku = skuMap.get(itemCmd.getSkuId());
            OrderItem orderItem = orderConvertor.toOrderItemDomainObject(itemCmd, sku);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    private Map<Long, Sku> requestSkuList(List<Long> skuIds) {
        List<Sku> skuInfoList = skuGateway.getSkuList(skuIds);

        ParamsChecker
                .throwIfIsTrue(CollUtil.isEmpty(skuInfoList) || skuIds.size() != skuInfoList.size(),
                ExceptionFactory.userException("商品库存不足或已下架"));

        return skuInfoList
                .stream()
                .collect(Collectors.toMap(Sku::getId, Function.identity()));
    }

    private Order assembleOrder(OrderCreateCmd orderCreateCmd, String tradeNo) {

        List<OrderItem> orderItemList = assembleOrderItems(orderCreateCmd);

        // 计算总实付金额
        int totalAmount = orderItemList.stream().mapToInt(OrderItem::getActualAmount).sum();

        Order order = new Order();
        // 设置订单基本信息
        order.setTradeNo(tradeNo);
        order.setOrderType(orderCreateCmd.getOrderType());
        order.setOrderChannel(orderCreateCmd.getOrderChannel());
        order.setOrderStatus(OrderStatus.WAIT_PAY);
        order.setBuyerRemark(orderCreateCmd.getBuyerRemark());
        order.setBuyerId(orderCreateCmd.getBuyerId() != null ? orderCreateCmd.getBuyerId() : ServiceContext.getCurrentUser().getUserId());
        order.setSellerId(orderCreateCmd.getSellerId() != null ? orderCreateCmd.getSellerId() : 0L);

        // 订单支付信息
        OrderPay orderPay = new OrderPay();
        orderPay.setPayStatus(OrderPay.PayStatus.PENDING_PAY);
        order.setOrderPay(orderPay);

        // 订单金额信息
        OrderAmount orderAmount = new OrderAmount();
        orderAmount.setExpectAmount(totalAmount);
        orderAmount.setActualAmount(totalAmount);
        orderAmount.setFreightAmount(0);
        order.setOrderAmount(orderAmount);

        // 设置订单项
        order.setOrderItemList(orderItemList);
        return order;
    }

    private Long saveOrder(Order order) {
        orderGateway.save(order);
        return order.getOrderId();
    }

    private void saveReceive(OrderCreateCmd reqDTO, Long orderId) {
        OrderCreateReceiveCreateCmd receiveInfo = reqDTO.getReceiveInfo();
        if (receiveInfo != null) {
            Receive receive = receiveConvertor.convertToReceive(receiveInfo);
            receive.setOrderId(orderId);
            receiveGateway.save(receive);
        }
    }
}
