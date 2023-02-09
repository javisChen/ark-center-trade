package com.ark.center.trade.client.order.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderCreateCmd", description = "创建订单")
public class OrderCreateCmd implements Serializable {

    @ApiModelProperty(value = "订单类型 1-商城订单", required = true)
    @NotNull(message = "订单类型不能为空")
    private Integer orderType;

    @ApiModelProperty(value = "下单渠道 1-PC 2-APP 3-小程序", required = true)
    @NotNull(message = "下单渠道不能为空")
    private Integer orderChannel;

    @ApiModelProperty(value = "买家ID", required = true)
    private Long buyerId;

    @ApiModelProperty(value = "买家备注", required = false)
    private String buyerRemark;

    @ApiModelProperty(value = "卖家ID", required = true)
    private Long sellerId;

    @ApiModelProperty(value = "订单项", required = true)
    @Size(max = 1000, message = "订单项不能超过1000个")
    private List<OrderCreateItemCmd> orderItems;

    @ApiModelProperty(value = "收货信息", required = true)
    private OrderCreateReceiveCreateCmd receiveInfo;

}