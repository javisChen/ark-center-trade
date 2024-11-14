package com.ark.center.trade.client.pay.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 支付结果通知记录
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "PayNotifyRecordPageQueryReqDTO对象")
public class PayNotifyRecordPageQuery extends PagingQuery {


    @Schema(description = "业务订单号")
    private String bizOrderCode;

    @Schema(description = "支付订单号")
    private String payOrderCode;

    @Schema(description = "通知请求体")
    private String reqBody;

    @Schema(description = "支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]")
    private Integer status;

}
