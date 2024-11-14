package com.ark.center.trade.client.pay.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>
 * 支付类别表
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Data
@Schema(description = "PayTypeCreateReqDTO对象")
public class PayTypeCreateCommand implements Serializable {

    @Schema(description = "支付类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "支付类型名称不能为空")
    private String name;

    @Schema(description = "支付类型编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "支付类型编号不能为空")
    private String code;

    @Schema(description = "支付单描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "支付单描述信息不能为空")
    private String description;

}
