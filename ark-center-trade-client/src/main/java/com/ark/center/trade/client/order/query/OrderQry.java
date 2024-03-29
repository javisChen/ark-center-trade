package com.ark.center.trade.client.order.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OrderQry", description = "获取订单信息")
public class OrderQry extends PagingQuery {

    @Schema(name = "订单号")
    private String tradeNo;

    @Schema(name = "订单类型 enums[SHOP,商城订单,1]")
    private Integer orderType;

    @Schema(name = "下单渠道 enums[PC,PC,1;APP,APP,2;MINI,小程序,3]")
    private Integer orderChannel;

    @Schema(name = "订单状态 enums[PENDING_PAY,待支付,1;PENDING_DELIVER,待发货,2;PENDING_RECEIVE,待收货,3;PENDING_EVALUATE,待评价,4;SUCCESS,交易成功,5]")
    private Integer orderStatus;

    @Schema(name = "支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]")
    private Integer payStatus;

    @Schema(name = "支付类型编码")
    private String payTypeCode;

    @Schema(name = "支付流水号")
    private String payTradeNo;

    @Schema(name = "买家ID")
    private Long buyerId;

    @Schema(name = "卖家ID")
    private Long sellerId;

    @Schema(name = "是否查询订单子项")
    private Boolean withOrderItems = false;

    @Schema(name = "是否查询订单子项")
    private Boolean withReceive = false;

}
