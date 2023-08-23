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
@Schema(name = "订单项目", description = "订单项目")
public class OrderItemDTO implements Serializable {

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String tradeNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String spuName;

    /**
     * SKU ID
     */
    @Schema(description = "ID")
    private Long skuId;

    /**
     * SKU单价
     */
    @Schema(description = "SKU单价")
    private Integer price;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    private Integer quantity;

    /**
     * 应付金额
     */
    @Schema(description = "应付金额")
    private Integer expectAmount;

    /**
     * 实付金额
     */
    @Schema(description = "实付金额")
    private Integer actualAmount;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String picUrl;

    /**
     * SKU销售参数JSON
     */
    @Schema(description = "SKU销售参数JSON")
    private String specData;

}