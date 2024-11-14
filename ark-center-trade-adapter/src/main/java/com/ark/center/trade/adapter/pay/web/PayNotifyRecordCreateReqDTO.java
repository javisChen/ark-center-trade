package com.ark.center.trade.adapter.pay.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>
 * 支付结果通知记录
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Data
@Schema(description = "PayNotifyRecordCreateReqDTO对象")
public class PayNotifyRecordCreateReqDTO implements Serializable {


    @Schema(description = "业务订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "业务订单号不能为空")
    private String bizOrderCode;

    @Schema(description = "支付订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "支付订单号不能为空")
    private String payOrderCode;

    @Schema(description = "通知请求体", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "通知请求体不能为空")
    private String reqBody;

    @Schema(description = "支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]不能为空")
    private Integer status;

}
