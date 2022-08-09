package com.kt.cloud.order.module.orderitem.dto.response;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderItemRespDTO对象", description = "订单明细表")
public class OrderItemRespDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "订单ID", required = true)
    private Long orderId;

    @ApiModelProperty(value = "订单号", required = true)
    private String code;

    @ApiModelProperty(value = "SKU ID", required = true)
    private Long skuId;

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

}
