package com.ark.center.order.module.order.service;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ark.center.commodity.api.sku.response.SkuRespDTO;
import com.ark.center.order.acl.sku.SkuServiceFacade;
import com.ark.center.order.dao.entity.OrderDO;
import com.ark.center.order.dao.entity.OrderItemDO;
import com.ark.center.order.dao.entity.ReceiveDO;
import com.ark.center.order.dao.mapper.OrderMapper;
import com.ark.center.order.module.order.dto.request.OrderCreateDTO;
import com.ark.center.order.module.order.dto.request.OrderPageQueryReqDTO;
import com.ark.center.order.api.response.OrderDetailRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ark.center.order.module.orderitem.dto.request.OrderItemUpdateReqDTO;
import com.ark.center.order.module.receive.dto.request.ReceiveCreateReqDTO;
import com.ark.center.order.module.receive.service.ReceiveService;
import com.ark.center.pay.api.dto.mq.MQPayNotifyDTO;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.component.orm.mybatis.base.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService extends ServiceImpl<OrderMapper, OrderDO> implements IService<OrderDO> {

    private final ReceiveService receiveService;
    private final OrderItemService orderItemService;
    private final SkuServiceFacade skuServiceFacade;

    @Transactional(rollbackFor = Throwable.class)
    public Long createOrder(OrderCreateDTO reqDTO) {
        // 生成工单号
        String tradeNo = IdUtil.getSnowflakeNextIdStr();
        // 根据SkuId获取商品信息
        List<OrderItemDO> orderItemList = assembleOrderItems(reqDTO, tradeNo);
        // 计算总实付金额
        int totalAmount = orderItemList.stream().mapToInt(OrderItemDO::getActualAmount).sum();
        // 组装订单数据
        OrderDO orderDO = assembleOrderDO(reqDTO, tradeNo, totalAmount);
        // 保存订单
        Long orderId = saveOrder(orderDO);
        // 保存订单明细
        saveOrderItems(orderItemList, orderDO);
        // 保存收货信息
        saveReceive(reqDTO, orderId);
        return orderId;
    }

    @NotNull
    private List<OrderItemDO> assembleOrderItems(OrderCreateDTO reqDTO, String orderCode) {
        List<OrderItemUpdateReqDTO> orderItems = reqDTO.getOrderItems();

        Map<Long, SkuRespDTO> skuMap = getSkuMap(orderItems);

        List<OrderItemDO> orderItemList = Lists.newArrayList();
        for (OrderItemUpdateReqDTO orderItemDTO :orderItems) {
            OrderItemDO orderItemDO = new OrderItemDO();
            SkuRespDTO skuRespDTO = skuMap.get(orderItemDTO.getSkuId());
            orderItemDO.setSkuId(orderItemDTO.getSkuId());
            orderItemDO.setTradeNo(orderCode);
            orderItemDO.setPrice(skuRespDTO.getSalesPrice());
            orderItemDO.setPicUrl(skuRespDTO.getMainPicture());
            int amount = skuRespDTO.getSalesPrice() * orderItemDTO.getQuantity();
            orderItemDO.setExpectAmount(amount);
            // todo 后期开发计算优惠券等方法
            orderItemDO.setActualAmount(amount);
            orderItemDO.setQuantity(orderItemDTO.getQuantity());
            orderItemDO.setSpecData(JSON.toJSONString(skuRespDTO.getSpecList()));
            orderItemList.add(orderItemDO);
        }
        return orderItemList;
    }

    @NotNull
    private Map<Long, SkuRespDTO> getSkuMap(List<OrderItemUpdateReqDTO> orderItems) {
        List<Long> skuIds = CollUtil.map(orderItems, OrderItemUpdateReqDTO::getSkuId, true);
        List<SkuRespDTO> skuInfoList = skuServiceFacade.getSkuInfoList(skuIds);
        Assert.isTrue(CollUtil.isNotEmpty(skuInfoList), () -> ExceptionFactory.userException("SKU列表为空"));
        return skuInfoList
                .stream()
                .collect(Collectors.toMap(SkuRespDTO::getId, Function.identity()));
    }

    @NotNull
    private OrderDO assembleOrderDO(OrderCreateDTO reqDTO, String tradeNo, Integer totalAmount) {
        OrderDO orderDO = new OrderDO();
        orderDO.setTradeNo(tradeNo);
        orderDO.setOrderType(reqDTO.getOrderType());
        orderDO.setOrderChannel(reqDTO.getOrderChannel());
        orderDO.setOrderStatus(OrderDO.OrderStatus.PENDING_PAY.getValue());
        orderDO.setPayStatus(OrderDO.PayStatus.PENDING_PAY.getValue());
        orderDO.setExpectAmount(totalAmount);
        orderDO.setActualAmount(totalAmount);
        // todo 开发运费功能
        orderDO.setFreightAmount(0);
        orderDO.setBuyerRemark(reqDTO.getBuyerRemark());
        orderDO.setBuyerId(reqDTO.getBuyerId() != null ? reqDTO.getBuyerId() : ServiceContext.getCurrentUser().getUserId());
        orderDO.setSellerId(reqDTO.getSellerId() != null ? reqDTO.getSellerId() : 1L);
        return orderDO;
    }

    private Long saveOrder(OrderDO orderDO) {
        save(orderDO);
        return orderDO.getId();
    }

    private void saveOrderItems(List<OrderItemDO> orderItems, OrderDO order) {
        Long orderId = order.getId();
        for (OrderItemDO orderItem : orderItems) {
            orderItem.setOrderId(orderId);
            orderItem.setTradeNo(order.getTradeNo());
        }
        orderItemService.saveBatch(orderItems);
    }

    private void saveReceive(OrderCreateDTO reqDTO, Long orderId) {
        ReceiveCreateReqDTO receiveInfo = reqDTO.getReceiveInfo();
        if (receiveInfo != null) {
            receiveService.save(convertReceive(orderId, receiveInfo));
        }
    }

    private List<OrderItemDO> convertOrderItems(Long orderId, String orderCode, List<OrderItemUpdateReqDTO> orderItems) {
        List<OrderItemDO> orderItemList = Lists.newArrayList();
        for (OrderItemUpdateReqDTO orderItem :orderItems) {
            OrderItemDO orderItemDO = new OrderItemDO();
            orderItemDO.setOrderId(orderId);
            orderItemDO.setTradeNo(orderCode);
            orderItemDO.setQuantity(orderItem.getQuantity());
            orderItemList.add(orderItemDO);
        }
        return orderItemList;
    }

    private ReceiveDO convertReceive(Long orderId, ReceiveCreateReqDTO receiveInfo) {
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

    public PageResponse<OrderDetailRespDTO> getPageList(OrderPageQueryReqDTO queryDTO) {
        IPage<OrderDetailRespDTO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, OrderDetailRespDTO.class));
        return BeanConvertor.copyPage(page, OrderDetailRespDTO.class);
    }

    public Long updateOrder(OrderCreateDTO reqDTO) {
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
