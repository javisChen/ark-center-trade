package com.ark.center.order.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderDetailRespDTO", description = "订单表")
public class OrderItemRespDTO implements Serializable {

    @ApiModelProperty(value = "订单ID", required = true)
    private Long orderId;

    @ApiModelProperty(value = "SKU ID", required = true)
    private Long skuId;

    @ApiModelProperty(value = "商品名称", required = true)
    private String spuName;

    @ApiModelProperty(value = "SKU单价", required = true)
    private Integer price;

    @ApiModelProperty(value = "购买数量", required = true)
    private Integer quantity;

    @ApiModelProperty(value = "应付金额", required = true)
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额", required = true)
    private Integer actualAmount;

    @ApiModelProperty(value = "图片地址", required = true)
    private String picUrl;

    @ApiModelProperty(value = "SKU销售参数JSON", required = true)
    private String specData;
}
