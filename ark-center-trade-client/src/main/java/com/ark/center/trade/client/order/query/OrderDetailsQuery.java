package com.ark.center.trade.client.order.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "OrderDetailsQry", description = "获取订单详情")
public class OrderDetailsQuery {

    @Schema(name = "订单id")
    private Long id;

    @Schema(name = "订单号")
    private String tradeNo;

}
