package com.ark.center.trade.client.order.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author EOP
 * @since 2022-08-09
 */
@Data
@Schema(name = "OrderReceiveCmd", description = "订单确认收货")
public class OrderReceiveCmd implements Serializable {

    @Schema(name = "订单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单不能为空")
    private Long orderId;

}