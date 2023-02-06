package com.ark.center.trade.domain.order.gateway;

import com.ark.center.trade.domain.order.model.Sku;

import java.util.List;

public interface ReceiveGateway {

    /**
     * 获取SKU集合
     */
    List<Sku> getSkuList(List<Long> skuIds);

}
