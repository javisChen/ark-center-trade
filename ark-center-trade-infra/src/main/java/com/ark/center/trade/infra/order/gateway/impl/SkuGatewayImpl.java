package com.ark.center.trade.infra.order.gateway.impl;

import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.client.goods.query.SkuQry;
import com.ark.center.product.client.inventory.command.StockLockCmd;
import com.ark.center.trade.client.order.command.OrderCreateItemCmd;
import com.ark.center.trade.infra.order.gateway.SkuGateway;
import com.ark.center.trade.infra.order.model.Sku;
import com.ark.center.trade.infra.order.assembler.SkuConvertor;
import com.ark.center.trade.infra.order.gateway.impl.rpc.SkuRemoteApi;
import com.ark.center.trade.infra.order.gateway.impl.rpc.StockRemoteApi;
import com.ark.component.microservice.rpc.util.RpcUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SkuGatewayImpl implements SkuGateway {

    private final SkuRemoteApi skuRemoteApi;

    private final StockRemoteApi stockRemoteApi;

    private final SkuConvertor skuConvertor;

    @Override
    public List<Sku> querySkus(List<Long> skuIds) {
        SkuQry qry = new SkuQry();
        qry.setSkuIds(skuIds);
        List<SkuDTO> skuRespDTOList = RpcUtils.checkAndGetData(skuRemoteApi.querySkus(qry));
        return skuConvertor.toSku(skuRespDTOList);
    }

    @Override
    public Sku querySku(Long skuId) {
        return querySkus(Collections.singletonList(skuId)).getFirst();
    }

    @Override
    public void decreaseStock(List<OrderCreateItemCmd> orderItems) {
        StockLockCmd cmd = new StockLockCmd();
        List<StockLockCmd.Item> items = orderItems.stream()
                .map(item -> new StockLockCmd.Item(item.getSkuId(), item.getQuantity()))
                .toList();
        cmd.setItems(items);
        stockRemoteApi.lock(cmd);
    }

}
