package com.ark.center.trade.client.order.dto.info;

import com.ark.center.trade.client.order.dto.ReceiveDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@Schema(name = "OrderInfoDTO", description = "订单详情")
public class OrderInfoDTO implements Serializable {

    @Schema(name = "订单基本信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private OrderBaseDTO orderBase;

    @Schema(name = "订单费用信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private OrderChargeDTO orderCharge;

    @Schema(name = "订单收货信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private ReceiveDTO orderReceive;

    @Schema(name = "订单商品信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OrderCommodityDTO> orderCommodities;


}
