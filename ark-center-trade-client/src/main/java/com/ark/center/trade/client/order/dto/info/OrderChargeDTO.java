package com.ark.center.trade.client.order.dto.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderChargeDTO {

    @Schema(description = "应付金额（单位：分）", required = true)
    private Integer expectAmount;

    @Schema(description = "实付金额（单位：分）", required = true)
    private Integer actualAmount;

    @Schema(description = "运费金额（单位：分）", required = true)
    private Integer freightAmount;

}