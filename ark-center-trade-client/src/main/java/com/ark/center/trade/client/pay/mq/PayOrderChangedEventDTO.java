package com.ark.center.trade.client.pay.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PayOrderChangedEventDTO {

    @Schema(name = "业务订单号", description = "业务订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bizTradeNo;

    @Schema(name = "支付交易单号", description = "支付交易单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payTradeNo;

    @Schema(name = "支付类型编码", description = "支付类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payTypeCode;

    @Schema(name = "支付结果", description = "1-成功 2-失败", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer result;
}
