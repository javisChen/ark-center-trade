package com.ark.center.trade.client.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "OrderItemDTO", description = "订单项")
public class OrderItemDTO implements Serializable {

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String tradeNo;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String spuName;

    /**
     * SKU ID
     */
    @ApiModelProperty(value = "ID")
    private Long skuId;

    /**
     * SKU单价
     */
    @ApiModelProperty(value = "SKU单价")
    private Integer price;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

    /**
     * 应付金额
     */
    @ApiModelProperty(value = "应付金额")
    private Integer expectAmount;

    /**
     * 实付金额
     */
    @ApiModelProperty(value = "实付金额")
    private Integer actualAmount;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String picUrl;

    /**
     * SKU销售参数JSON
     */
    @ApiModelProperty(value = "SKU销售参数JSON")
    private String specData;

}