package com.ark.center.trade.application.order.executor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.ark.center.trade.application.order.event.OrderCreatedEvent;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderCreateItemCmd;
import com.ark.center.trade.client.order.command.OrderCreateReceiveCreateCmd;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.OrderItem;
import com.ark.center.trade.domain.order.OrderReceive;
import com.ark.center.trade.domain.order.enums.OrderStatus;
import com.ark.center.trade.domain.order.enums.PayStatus;
import com.ark.center.trade.domain.order.gateway.SkuGateway;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.domain.receive.gateway.OrderReceiveGateway;
import com.ark.center.trade.infra.order.assembler.OrderReceiveAssembler;
import com.ark.center.trade.infra.order.service.OrderService;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.security.base.user.LoginUser;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderCreateCmdExe {

    private final OrderService orderService;

    private final SkuGateway skuGateway;

    private final OrderReceiveGateway orderReceiveGateway;

    private final OrderReceiveAssembler orderReceiveAssembler;

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 交易单号生成器
     */
    private final TradeNoGenerator tradeNoGenerator = new DefaultTradeNoGenerator();

    public Long execute(OrderCreateCmd orderCreateCmd) {
        // 生成工单号
        String tradeNo = tradeNoGenerator.generate(orderCreateCmd);
        // 查询商品信息
        List<Sku> skus = retrieveSkus(orderCreateCmd.getOrderItems());
        // 库存扣减
        decreaseStock(orderCreateCmd.getOrderItems());
        // 组装订单数据
        List<OrderItem> orderItems = assembleOrderItems(skus, orderCreateCmd);
        // 组装订单数据
        Order order = assembleOrder(orderCreateCmd, orderItems, tradeNo);
        // 保存订单信息
        Long orderId = saveOrder(order, orderItems);
        // 保存收货信息
        saveReceive(orderCreateCmd, orderId);

        Long userId = ServiceContext.getCurrentUser().getUserId();
        eventPublisher.publishEvent(new OrderCreatedEvent(userId, order));

        return orderId;
    }

    private void decreaseStock(List<OrderCreateItemCmd> orderItems) {
        skuGateway.decreaseStock(orderItems);
    }

    private List<OrderItem> assembleOrderItems(List<Sku> skus, OrderCreateCmd orderCreateCmd) {

        Map<Long, Sku> skuMap = skus
                .stream()
                .collect(Collectors.toMap(Sku::getId, Function.identity()));

        List<OrderCreateItemCmd> orderItems = orderCreateCmd.getOrderItems();
        List<OrderItem> orderItemList = Lists.newArrayList();
        for (OrderCreateItemCmd itemCmd : orderItems) {
            Sku sku = skuMap.get(itemCmd.getSkuId());
            // 订单项价格 = 销售价 * 数量
            int totalAmount = sku.getSalesPrice() * itemCmd.getQuantity();
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuName(sku.getSkuName());
            orderItem.setSkuId(itemCmd.getSkuId());
            orderItem.setPrice(sku.getSalesPrice());
            orderItem.setQuantity(itemCmd.getQuantity());
            orderItem.setExpectAmount(totalAmount);
            orderItem.setActualAmount(totalAmount);
            orderItem.setPicUrl(sku.getMainPicture());
            orderItem.setSpecs(JSON.toJSONString(sku.getSpecs()));
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    private List<Sku> retrieveSkus(List<OrderCreateItemCmd> orderItems) {
        List<Long> skuIds = CollUtil.map(orderItems, OrderCreateItemCmd::getSkuId, true);
        List<Sku> skuInfoList = skuGateway.querySkus(skuIds);
        // todo 这里需要优化为列出无效商品的明细
        Assert.isTrue(CollUtil.isNotEmpty(skuInfoList) && skuIds.size() == skuInfoList.size(),
                () -> ExceptionFactory.userException("商品库存不足或已下架"));
        return skuInfoList;

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
        orderService.save(order, orderItems);
        return order.getId();
    }

    private void saveReceive(OrderCreateCmd reqDTO, Long orderId) {
        OrderCreateReceiveCreateCmd receiveInfo = reqDTO.getReceiveInfo();
        if (receiveInfo != null) {
            OrderReceive orderReceive = orderReceiveAssembler.convertToReceive(receiveInfo);
            orderReceive.setOrderId(orderId);
            orderReceiveGateway.save(orderReceive);
        }
    }
}
