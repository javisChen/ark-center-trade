package com.ark.center.trade.client.pay.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "PayTypeUpdateReqDTO对象")
public class PayTypeUpdateCommand implements Serializable {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "支付类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "支付类型名称不能为空")
    private String name;

    @Schema(description = "支付类型编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "支付类型编号不能为空")
    private String code;

    @Schema(description = "支付单描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "支付单描述信息不能为空")
    private String description;

}