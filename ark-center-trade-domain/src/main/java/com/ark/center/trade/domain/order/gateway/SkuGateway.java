package com.ark.center.trade.domain.order.gateway;

import com.ark.center.trade.domain.order.model.Sku;

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

}
