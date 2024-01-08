package com.ark.center.trade.client.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@Schema(name = "OrderItemDTO", description = "订单项")
public class OrderItemDTO implements Serializable {

    @Schema(name = "订单id")
    private Long orderId;

    /**
     * 订单号
     */
    @Schema(name = "订单号")
    private String tradeNo;

    /**
     * 商品名称
     */
    @Schema(name = "商品名称")
    private String productName;

    /**
     * SKU ID
     */
    @Schema(name = "ID")
    private Long skuId;

    /**
     * SKU单价
     */
    @Schema(name = "SKU单价")
    private Integer price;

    /**
     * 购买数量
     */
    @Schema(name = "购买数量")
    private Integer quantity;

    /**
     * 应付金额
     */
    @Schema(name = "应付金额")
    private Integer expectAmount;

    /**
     * 实付金额
     */
    @Schema(name = "实付金额")
    private Integer actualAmount;

    /**
     * 图片地址
     */
    @Schema(name = "图片地址")
    private String picUrl;

    /**
     * SKU销售参数JSON
     */
    @Schema(name = "SKU销售参数JSON")
    private String specs;

}