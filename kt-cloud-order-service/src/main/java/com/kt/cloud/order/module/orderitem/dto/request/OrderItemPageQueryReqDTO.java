package com.kt.cloud.order.module.orderitem.dto.request;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OrderItemPageQueryReqDTO对象", description = "订单明细表")
public class OrderItemPageQueryReqDTO extends PagingQuery {


    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String code;

    @ApiModelProperty(value = "SKU ID")
    private Long skuId;

    @ApiModelProperty(value = "SKU单价")
    private Integer price;

    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

    @ApiModelProperty(value = "应付金额")
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额")
    private Integer actualAmount;

    @ApiModelProperty(value = "图片地址")
    private String picUrl;

}
