package com.ark.center.trade.client.order.dto.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderChargeDTO {

    @Schema(name = "应付金额（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer expectAmount;

    @Schema(name = "实付金额（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer actualAmount;

    @Schema(name = "运费金额（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer freightAmount;

}