package com.ark.center.trade.client.order.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "OrderDeliverCmd", description = "订单发货")
public class OrderDeliverCmd implements Serializable {

    @Schema(name = "订单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单不能为空")
    private Long orderId;

    @Schema(name = "物流公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "物流公司不能为空")
    private String logisticsCompany;

    @Schema(name = "物流单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "物流单号不能为空")
    private String logisticsCode;

}