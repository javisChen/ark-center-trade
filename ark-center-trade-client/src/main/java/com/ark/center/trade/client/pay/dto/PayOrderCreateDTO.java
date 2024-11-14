package com.ark.center.trade.client.pay.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 支付订单信息
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Data
@Schema(description = "PayOrderCreateRespDTO")
public class PayOrderCreateDTO implements Serializable {

    @Schema(description = "支付单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long payOrderId;

    @Schema(description = "支付单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payTradeNo;

    @Schema(description = "业务订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bizTradeNo;

    @Schema(description = "支付类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payTypeCode;

}
