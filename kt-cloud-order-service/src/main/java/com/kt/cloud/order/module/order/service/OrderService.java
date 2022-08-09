package com.kt.cloud.order.module.order.service;
import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Lists;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.order.dao.entity.OrderDO;
import com.kt.cloud.order.dao.entity.OrderItemDO;
import com.kt.cloud.order.dao.entity.ReceiveDO;
import com.kt.cloud.order.dao.mapper.OrderMapper;
import com.kt.cloud.order.module.order.dto.request.OrderUpdateReqDTO;
import com.kt.cloud.order.module.order.dto.request.OrderPageQueryReqDTO;
import com.kt.cloud.order.module.order.dto.response.OrderRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.cloud.order.module.orderitem.dto.request.OrderItemUpdateReqDTO;
import com.kt.cloud.order.module.orderitem.service.OrderItemService;
import com.kt.cloud.order.module.receive.dto.request.ReceiveCreateReqDTO;
import com.kt.cloud.order.module.receive.service.ReceiveService;
import com.kt.component.dto.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;
import com.kt.component.orm.mybatis.base.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class OrderService extends ServiceImpl<OrderMapper, OrderDO> implements IService<OrderDO> {

    private final ReceiveService receiveService;
    private final OrderItemService orderItemService;

    public OrderService(ReceiveService receiveService, OrderItemService orderItemService) {
        this.receiveService = receiveService;
        this.orderItemService = orderItemService;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Long createOrder(OrderUpdateReqDTO reqDTO) {

        // 生成工单号
        String code = IdUtil.getSnowflakeNextIdStr();
        // 根据SKU_ID获取商品信息
        // 计算各项价格

        // 计算订单总价格
        Integer amount = 0;
        // 组装订单数据
        OrderDO orderDO = assembleOrderDO(reqDTO, code, amount);
        // 保存订单
        Long orderId = saveOrder(orderDO);
        // 保存订单明细
        saveOrderItems(reqDTO, orderDO);

        // 保存收货信息
        saveReceive(reqDTO, orderId);
        return orderId;
    }

    @NotNull
    private OrderDO assembleOrderDO(OrderUpdateReqDTO reqDTO, String code, Integer amount) {
        OrderDO orderDO = new OrderDO();
        orderDO.setCode(code);
        orderDO.setOrderType(reqDTO.getOrderType());
        orderDO.setOrderChannel(reqDTO.getOrderChannel());
        orderDO.setOrderStatus(OrderDO.OrderStatus.PENDING_PAY.getValue());
        orderDO.setPayStatus(OrderDO.PayStatus.PENDING_PAY.getValue());
        orderDO.setExpectAmount(amount);
        orderDO.setActualAmount(amount);
        orderDO.setFreightAmount(0);
        orderDO.setBuyerRemark(reqDTO.getBuyerRemark());
        orderDO.setBuyerId(reqDTO.getBuyerId());
        orderDO.setSellerId(reqDTO.getSellerId());
        return orderDO;
    }

    private Long saveOrder(OrderDO orderDO) {
        save(orderDO);
        return orderDO.getId();
    }

    private void saveOrderItems(OrderUpdateReqDTO reqDTO, OrderDO order) {
        orderItemService.saveBatch(convertOrderItems(order.getId(), order.getCode(), reqDTO.getOrderItems()));
    }

    private void saveReceive(OrderUpdateReqDTO reqDTO, Long orderId) {
        receiveService.save(convertReceive(orderId, reqDTO.getReceiveInfo()));
    }

    private List<OrderItemDO> convertOrderItems(Long orderId, String orderCode, List<OrderItemUpdateReqDTO> orderItems) {
        List<OrderItemDO> orderItemList = Lists.newArrayList();
        for (OrderItemUpdateReqDTO orderItem :orderItems) {
            OrderItemDO orderItemDO = new OrderItemDO();
            orderItemDO.setOrderId(orderId);
            orderItemDO.setOrderCode(orderCode);
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
        return receiveDO;
    }

    public PageResponse<OrderRespDTO> getPageList(OrderPageQueryReqDTO queryDTO) {
        IPage<OrderRespDTO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, OrderRespDTO.class));
        return BeanConvertor.copyPage(page, OrderRespDTO.class);
    }

    public Long updateOrder(OrderUpdateReqDTO reqDTO) {
        OrderDO entity = BeanConvertor.copy(reqDTO, OrderDO.class);
        updateById(entity);
        return entity.getId();
    }

    public OrderRespDTO getOrderInfo(Long OrderId) {
        OrderDO entity = getById(OrderId);
        return BeanConvertor.copy(entity, OrderRespDTO.class);
    }

}
