package com.ark.center.trade.application.order.executor;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderCreateItemCmd;
import com.ark.center.trade.client.order.command.OrderCreateReceiveCreateCmd;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.ark.center.trade.domain.order.OrderStatus;
import com.ark.center.trade.domain.order.PayStatus;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.order.gateway.ReceiveGateway;
import com.ark.center.trade.domain.order.gateway.SkuGateway;
import com.ark.center.trade.domain.order.model.Receive;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.infra.order.stm.TradeOrderStateMachine;
import com.ark.center.trade.infra.receive.convertor.ReceiveConvertor;
import com.ark.component.common.ParamsChecker;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.security.base.user.LoginUser;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    private final TradeOrderStateMachine tradeOrderStateMachine;

    /**
     * 交易单号生成器
     */
    private final TradeNoGenerator tradeNoGenerator = new DefaultTradeNoGenerator();

    public Long execute(OrderCreateCmd orderCreateCmd) {
        // 生成工单号
        String tradeNo = tradeNoGenerator.generate(orderCreateCmd);
        // 组装订单数据
        List<OrderItem> orderItems = assembleOrderItems(orderCreateCmd);
        // 组装订单数据
        Order order = assembleOrder(orderCreateCmd, orderItems, tradeNo);
        // 保存订单信息
        Long orderId = saveOrder(order, orderItems);
        // 保存收货信息
        saveReceive(orderCreateCmd, orderId);
        return orderId;
    }

    private List<OrderItem> assembleOrderItems(OrderCreateCmd orderCreateCmd) {
        List<OrderCreateItemCmd> orderItems = orderCreateCmd.getOrderItems();
        List<Long> skuIds = CollUtil.map(orderItems, OrderCreateItemCmd::getSkuId, true);
        Map<Long, Sku> skuMap = querySkus(skuIds);
        List<OrderItem> orderItemList = Lists.newArrayList();
        for (OrderCreateItemCmd itemCmd :orderItems) {
            Sku sku = skuMap.get(itemCmd.getSkuId());
            // 订单项价格 = 销售价 * 数量
            int totalAmount = sku.getSalesPrice() * itemCmd.getQuantity();
            OrderItem orderItem = new OrderItem();
            orderItem.setProductName(sku.getSpuName());
            orderItem.setSkuId(itemCmd.getSkuId());
            orderItem.setPrice(sku.getSalesPrice());
            orderItem.setQuantity(itemCmd.getQuantity());
            orderItem.setExpectAmount(totalAmount);
            orderItem.setActualAmount(totalAmount);
            orderItem.setPicUrl(sku.getMainPicture());
            orderItem.setSpecData(JSON.toJSONString(sku.getSpecList()));
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    private Map<Long, Sku> querySkus(List<Long> skuIds) {
        List<Sku> skuInfoList = skuGateway.querySkus(skuIds);

        ParamsChecker
                .throwIfIsTrue(CollUtil.isEmpty(skuInfoList) || skuIds.size() != skuInfoList.size(),
                ExceptionFactory.userException("商品库存不足或已下架"));

        return skuInfoList
                .stream()
                .collect(Collectors.toMap(Sku::getId, Function.identity()));
    }

    private Order assembleOrder(OrderCreateCmd orderCreateCmd, List<OrderItem> orderItems, String tradeNo) {

        LoginUser loginUser = ServiceContext.getCurrentUser();

        // 计算总实付金额
        int totalAmount = calcTotalAmount(orderItems);

        Order order = new Order();

        // 设置订单基本信息
        order.setTradeNo(tradeNo);
        order.setOrderType(orderCreateCmd.getOrderType());
        order.setOrderChannel(orderCreateCmd.getOrderChannel());
        order.setOrderStatus(OrderStatus.WAIT_PAY.getValue());
        order.setBuyerRemark(orderCreateCmd.getBuyerRemark());
        order.setBuyerId(orderCreateCmd.getBuyerId() != null ? orderCreateCmd.getBuyerId() : loginUser.getUserId());
        order.setBuyerName(StringUtils.defaultString(orderCreateCmd.getBuyerName(), loginUser.getUsername()));
        order.setSellerId(orderCreateCmd.getSellerId() != null ? orderCreateCmd.getSellerId() : 0L);
        // 订单支付信息
        order.setPayStatus(PayStatus.PENDING_PAY.getValue());
        // 订单金额信息
        order.setExpectAmount(totalAmount);
        order.setActualAmount(totalAmount);
        order.setFreightAmount(0);
        return order;
    }

    private int calcTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream().mapToInt(OrderItem::getActualAmount).sum();
    }

    private Long saveOrder(Order order, List<OrderItem> orderItems) {
        orderGateway.save(order, orderItems);
        return order.getId();
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
