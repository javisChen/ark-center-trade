package com.ark.center.trade.client.order.dto.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderChargeDTO {

    @ApiModelProperty(value = "应付金额（单位：分）", required = true)
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额（单位：分）", required = true)
    private Integer actualAmount;

    @ApiModelProperty(value = "运费金额（单位：分）", required = true)
    private Integer freightAmount;

}