package com.kt.cloud.order.module.orderitem.dto.request;

import java.time.LocalDateTime;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
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
@ApiModel(value = "OrderItemCreateReqDTO对象", description = "订单明细表")
public class OrderItemCreateReqDTO implements Serializable {



    @ApiModelProperty(value = "订单ID", required = true)
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @ApiModelProperty(value = "订单号", required = true)
    @NotBlank(message = "订单号不能为空")
    private String code;

    @ApiModelProperty(value = "SKU ID", required = true)
    @NotNull(message = "SKU ID不能为空")
    private Long skuId;

    @ApiModelProperty(value = "SKU单价", required = true)
    @NotNull(message = "SKU单价不能为空")
    private Integer price;

    @ApiModelProperty(value = "购买数量", required = true)
    @NotNull(message = "购买数量不能为空")
    private Integer quantity;

    @ApiModelProperty(value = "应付金额", required = true)
    @NotNull(message = "应付金额不能为空")
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额", required = true)
    @NotNull(message = "实付金额不能为空")
    private Integer actualAmount;

    @ApiModelProperty(value = "图片地址", required = true)
    @NotBlank(message = "图片地址不能为空")
    private String picUrl;

}
