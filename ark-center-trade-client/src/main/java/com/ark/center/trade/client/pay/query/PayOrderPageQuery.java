package com.ark.center.trade.client.pay.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
/**
 * <p>
 * 支付订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "PayOrderPageQueryReqDTO对象")
public class PayOrderPageQuery extends PagingQuery {

    @Schema(description = "业务订单号")
    private String bizOrderCode;

    @Schema(description = "支付订单号")
    private String payOrderCode;

    @Schema(description = "支付类型编码")
    private String payTypeCode;

    @Schema(description = "支付金额")
    private Integer amount;

    @Schema(description = "支付单描述信息")
    private String description;

    @Schema(description = "支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]")
    private Integer status;

    @Schema(description = "最后一次支付结果通知时间")
    private LocalDateTime lastNotifyTime;

}
