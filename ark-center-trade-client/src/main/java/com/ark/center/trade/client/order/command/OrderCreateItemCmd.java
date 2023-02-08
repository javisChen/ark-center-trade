package com.ark.center.trade.client.order.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderCreateItemCmd", description = "订单明细")
public class OrderCreateItemCmd implements Serializable {

    @ApiModelProperty(value = "SkuId", required = true)
    private Long skuId;

    @ApiModelProperty(value = "购买数量", required = true)
    private Integer quantity;

}
