package com.ark.center.trade.client.order.dto;

import lombok.Data;

@Data
public class OrderProductDTO {

    /**
     * 商品名称
     */
    private String productName;

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
     * 商品属性
     */
    private String specData;

}
