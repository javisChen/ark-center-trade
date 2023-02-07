package com.ark.center.trade.domain.order.model;

import lombok.Data;

import java.util.Map;

@Data
public class OrderItem {


    /**
     * 商品名称
     */
    private String spuName;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * SKU单价
     */
    private Integer price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 应付金额
     */
    private Integer expectAmount;

    /**
     * 实付金额
     */
    private Integer actualAmount;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * SKU销售参数JSON
     */
    private Map<String, Object> specData;

}
