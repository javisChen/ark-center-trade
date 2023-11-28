package com.ark.center.trade.client.order.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@Schema(name = "OrderCommonQry", description = "订单获取公共条件")
public class OrderCommonQry {

    @Schema(name = "是否查询订单子项")
    private boolean withOrderItems = false;

    @Schema(name = "是否查询收货地址")
    private boolean withReceive = false;

}
