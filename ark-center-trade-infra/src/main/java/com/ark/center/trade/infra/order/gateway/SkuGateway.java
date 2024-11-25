package com.ark.center.trade.infra.order.gateway;

import com.ark.center.trade.client.order.command.OrderCreateItemCmd;
import com.ark.center.trade.infra.order.model.Sku;

import java.util.List;

public interface SkuGateway {

    /**
     * 查询Sku
     */
    List<Sku> querySkus(List<Long> skuIds);

    /**
     * 查询Sku
     */
    Sku querySku(Long skuId);

    /**
     * 商品库存扣减
     */
    void decreaseStock(List<OrderCreateItemCmd> orderItems);

}
