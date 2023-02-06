package com.ark.center.trade.infra.order.gateway.impl;

import com.ark.center.commodity.api.sku.request.SkuInfoGetReqDTO;
import com.ark.center.commodity.api.sku.response.SkuRespDTO;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.domain.order.gateway.SkuGateway;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.infra.order.convertor.OrderConvertor;
import com.ark.center.trade.infra.order.convertor.SkuConvertor;
import com.ark.center.trade.infra.order.gateway.impl.db.OrderMapper;
import com.ark.center.trade.infra.order.gateway.impl.rpc.SkuServiceApi;
import com.ark.component.microservice.rpc.util.RpcUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {

    private OrderMapper orderMapper;

    @Override
    public boolean save(Order order) {
        OrderConvertor.toDataObject()
        orderMapper.insert(order)
        return false;
    }
}
