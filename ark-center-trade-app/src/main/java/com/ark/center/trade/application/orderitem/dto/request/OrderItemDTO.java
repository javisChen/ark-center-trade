package com.ark.center.trade.application.orderitem.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

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
@ApiModel(value = "OrderItemUpdateReqDTO对象", description = "订单明细表")
public class OrderItemDTO implements Serializable {

    @ApiModelProperty(value = "SKU ID", required = true)
    @NotNull(message = "SKU ID不能为空")
    private Long skuId;

    @ApiModelProperty(value = "购买数量", required = true)
    @NotNull(message = "购买数量不能为空")
    private Integer quantity;

}