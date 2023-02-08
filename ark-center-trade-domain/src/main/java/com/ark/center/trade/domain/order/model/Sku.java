package com.ark.center.trade.domain.order.model;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Sku {


    /**
     * 商品名称
     */
    private Long skuId;


    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 销售价（单位：分）
     */
    private Integer salesPrice;

    /**
     * 成本价（单位：分）
     */
    private Integer costPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 预警库存
     */
    private Integer warnStock;

    /**
     * 图片地址
     */
    private String mainPicture;

    /**
     * SKU规格属性列表
     */
    private List<SkuAttr> specList;

    public Map<String, Object> specListToMap() {
        return this.specList.stream().collect(Collectors.toMap(SkuAttr::getAttrName, SkuAttr::getAttrValue));
    }
}
