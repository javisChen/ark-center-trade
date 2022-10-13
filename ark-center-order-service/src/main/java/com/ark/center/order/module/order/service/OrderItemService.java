package com.ark.center.order.module.order.service;

import com.ark.center.order.dao.entity.OrderItemDO;
import com.ark.center.order.dao.mapper.OrderItemMapper;
import com.ark.center.order.api.response.OrderItemRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Service
public class OrderItemService extends ServiceImpl<OrderItemMapper, OrderItemDO> implements IService<OrderItemDO> {

    public List<OrderItemRespDTO> listOrderItems(Long orderId) {
        List<OrderItemDO> doList = lambdaQuery()
                .eq(OrderItemDO::getOrderId, orderId)
                .list();
        return doList.stream().map(item -> {
            OrderItemRespDTO orderItemRespDTO = new OrderItemRespDTO();
            orderItemRespDTO.setOrderId(item.getOrderId());
            orderItemRespDTO.setSkuId(item.getSkuId());
            orderItemRespDTO.setSpuName(item.getSpuName());
            orderItemRespDTO.setPrice(item.getPrice());
            orderItemRespDTO.setQuantity(item.getQuantity());
            orderItemRespDTO.setExpectAmount(item.getExpectAmount());
            orderItemRespDTO.setActualAmount(item.getActualAmount());
            orderItemRespDTO.setPicUrl(item.getPicUrl());
            orderItemRespDTO.setSpecData(item.getSpecData());
            return orderItemRespDTO;
        }).collect(Collectors.toList());
    }
}
