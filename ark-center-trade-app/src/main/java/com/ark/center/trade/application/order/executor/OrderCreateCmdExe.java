package com.ark.center.trade.application.order.executor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.ark.center.pay.api.dto.mq.MQPayNotifyDTO;
import com.ark.center.trade.application.orderitem.dto.request.OrderItemDTO;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderCreateItemCmd;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.client.order.command.OrderCreateReceiveCreateCmd;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.order.gateway.SkuGateway;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.domain.order.model.vo.OrderAmount;
import com.ark.center.trade.domain.order.model.vo.OrderPay;
import com.ark.center.trade.infra.order.gateway.impl.db.OrderDO;
import com.ark.center.trade.infra.order.gateway.impl.db.OrderItemDO;
import com.ark.center.trade.infra.receive.gateway.impl.db.ReceiveDO;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    public Long execute(OrderCreateCmd orderCreateCmd) {
        // 生成工单号
        String tradeNo = IdUtil.getSnowflakeNextIdStr();
        // 组装订单数据
        Order order = assembleOrderDO(orderCreateCmd, tradeNo);
        // 保存订单信息
        Long orderId = saveOrder(order);
        // 保存收货信息
        saveReceive(orderCreateCmd, orderId);
        return orderId;
    }

    private List<OrderItemDO> assembleOrderItems(OrderCreateCmd reqDTO, String orderCode) {
        List<OrderCreateItemCmd> orderItems = reqDTO.getOrderItems();
        Map<Long, Sku> skuMap = getSkuListMap(orderItems);
        List<OrderItemDO> orderItemList = Lists.newArrayList();
        for (OrderCreateItemCmd orderItemDTO :orderItems) {
            OrderItemDO orderItemDO = new OrderItemDO();
            Sku sku = skuMap.get(orderItemDTO.getSkuId());
            orderItemDO.setSkuId(orderItemDTO.getSkuId());
            orderItemDO.setTradeNo(orderCode);
            orderItemDO.setPrice(sku.getSalesPrice());
            orderItemDO.setPicUrl(sku.getMainPicture());
            int amount = sku.getSalesPrice() * orderItemDTO.getQuantity();
            orderItemDO.setExpectAmount(amount);
            // todo 后期开发计算优惠券等方法
            orderItemDO.setActualAmount(amount);
            orderItemDO.setQuantity(orderItemDTO.getQuantity());
            orderItemDO.setSpecData(JSON.toJSONString(sku.getSpecList()));
            orderItemList.add(orderItemDO);
        }
        return orderItemList;
    }

    private Map<Long, Sku> getSkuListMap(List<OrderCreateItemCmd> orderItems) {
        List<Long> skuIds = CollUtil.map(orderItems, OrderCreateItemCmd::getSkuId, true);
        List<Sku> skuInfoList = skuGateway.getSkuList(skuIds);
        Assert.isTrue(CollUtil.isNotEmpty(skuInfoList), () -> ExceptionFactory.userException("SKU列表为空"));
        return skuInfoList
                .stream()
                .collect(Collectors.toMap(Sku::getSkuId, Function.identity()));
    }

    private Order assembleOrderDO(OrderCreateCmd orderCreateCmd, String tradeNo) {

        List<OrderItemDO> orderItemList = assembleOrderItems(orderCreateCmd, tradeNo);

        // 计算总实付金额
        int totalAmount = orderItemList.stream().mapToInt(OrderItemDO::getActualAmount).sum();

        Order order = new Order();
        // 设置订单基本信息
        order.setTradeNo(tradeNo);
        order.setOrderType(orderCreateCmd.getOrderType());
        order.setOrderChannel(orderCreateCmd.getOrderChannel());
        order.setOrderStatus(Order.OrderStatus.PENDING_PAY);
        order.setBuyerRemark(orderCreateCmd.getBuyerRemark());
        order.setBuyerId(orderCreateCmd.getBuyerId() != null ? orderCreateCmd.getBuyerId() : ServiceContext.getCurrentUser().getUserId());
        order.setSellerId(orderCreateCmd.getSellerId() != null ? orderCreateCmd.getSellerId() : 1L);

        // 订单支付信息
        OrderPay orderPay = new OrderPay();
        orderPay.setPayStatus(OrderPay.PayStatus.PENDING_PAY);
        order.setOrderPay(orderPay);

        // 订单金额信息
        OrderAmount orderAmount = new OrderAmount();
        orderAmount.setExpectAmount(totalAmount);
        orderAmount.setActualAmount(totalAmount);
        orderAmount.setFreightAmount(0);
        return order;
    }

    private Long saveOrder(Order order) {
        orderGateway.save(order);
        return order.getOrderId();
    }

    private void saveOrderItems(List<OrderItemDO> orderItems, OrderDO order) {
        Long orderId = order.getId();
        for (OrderItemDO orderItem : orderItems) {
            orderItem.setOrderId(orderId);
            orderItem.setTradeNo(order.getTradeNo());
        }
        orderItemService.saveBatch(orderItems);
    }

    private void saveReceive(OrderCreateCmd reqDTO, Long orderId) {
        OrderCreateReceiveCreateCmd receiveInfo = reqDTO.getReceiveInfo();
        if (receiveInfo != null) {
            receiveService.save(convertReceive(orderId, receiveInfo));
        }
    }

    private List<OrderItemDO> convertOrderItems(Long orderId, String orderCode, List<OrderItemDTO> orderItems) {
        List<OrderItemDO> orderItemList = Lists.newArrayList();
        for (OrderItemDTO orderItem :orderItems) {
            OrderItemDO orderItemDO = new OrderItemDO();
            orderItemDO.setOrderId(orderId);
            orderItemDO.setTradeNo(orderCode);
            orderItemDO.setQuantity(orderItem.getQuantity());
            orderItemList.add(orderItemDO);
        }
        return orderItemList;
    }

    private ReceiveDO convertReceive(Long orderId, OrderCreateReceiveCreateCmd receiveInfo) {
        ReceiveDO receiveDO = new ReceiveDO();
        receiveDO.setOrderId(orderId);
        receiveDO.setName(receiveInfo.getName());
        receiveDO.setMobile(receiveInfo.getMobile());
        receiveDO.setProvince(receiveInfo.getProvince());
        receiveDO.setCity(receiveInfo.getCity());
        receiveDO.setDistrict(receiveInfo.getDistrict());
        receiveDO.setAddress(receiveInfo.getAddress());
        receiveDO.setStreet(receiveInfo.getStreet());
        return receiveDO;
    }

    public PageResponse<OrderDetailRespDTO> getPageList(OrderPageQry queryDTO) {
        IPage<OrderDetailRespDTO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, OrderDetailRespDTO.class));
        return BeanConvertor.copyPage(page, OrderDetailRespDTO.class);
    }

    public Long updateOrder(OrderCreateCmd reqDTO) {
        OrderDO entity = BeanConvertor.copy(reqDTO, OrderDO.class);
        updateById(entity);
        return entity.getId();
    }

    public OrderDetailRespDTO getOrderInfo(Long orderId) {
        OrderDO entity = getById(orderId);
        OrderDetailRespDTO detailRespDTO = BeanConvertor.copy(entity, OrderDetailRespDTO.class);
        detailRespDTO.setOrderItems(orderItemService.listOrderItems(orderId));
        detailRespDTO.setReceiveInfo(receiveService.getReceiveInfoByOrderId(orderId));
        return detailRespDTO;
    }

    public void updateOrderOnPaySuccess(MQPayNotifyDTO payNotifyDTO) {
        lambdaUpdate()
                .eq(OrderDO::getTradeNo, payNotifyDTO.getBizTradeNo())
                .set(OrderDO::getPayTradeNo, payNotifyDTO.getPayTradeNo())
                .set(OrderDO::getPayStatus, OrderDO.PayStatus.PAY_SUCCESS.getValue())
                .set(OrderDO::getOrderStatus, OrderDO.OrderStatus.PENDING_DELIVER.getValue())
                .update();
    }
}
