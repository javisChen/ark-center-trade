package com.ark.center.trade.client.pay.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "支付单创建命令")
public class PayOrderCreateCommand implements Serializable {

    @Schema(description = "业务交易单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "业务交易单号不能为空")
    private String bizTradeNo;

    @Schema(description = "支付类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "支付类型编码不能为空")
    private String payTypeCode;

    @Schema(description = "支付类型id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "支付类型id不能为空")
    private Integer payTypeId;

    @Schema(description = "支付单描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

}