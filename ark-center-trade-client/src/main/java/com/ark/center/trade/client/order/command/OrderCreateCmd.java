package com.ark.center.trade.client.order.command;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
@Schema(name = "OrderCreateCmd", description = "创建订单")
public class OrderCreateCmd implements Serializable {

    @Schema(name = "订单类型 1-商城订单", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单类型不能为空")
    private Integer orderType;

    @Schema(name = "下单渠道 1-PC 2-APP 3-小程序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "下单渠道不能为空")
    private Integer orderChannel;

    @Schema(name = "买家ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long buyerId;

    @Schema(name = "买家名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String buyerName;

    @Schema(name = "买家备注", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String buyerRemark;

    @Schema(name = "卖家ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sellerId;

    @Schema(name = "订单项", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 1000, message = "订单项不能超过1000个")
    private List<OrderCreateItemCmd> orderItems;

    @Schema(name = "收货信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private OrderCreateReceiveCreateCmd receiveInfo;

}