package com.ark.center.trade.client.order.query;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ark.component.dto.PagingQuery;
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
@ApiModel(value = "OrderPageQueryReqDTO对象", description = "订单表")
public class OrderPageQry extends PagingQuery {


    @Schema(description = "订单号")
    private String code;

    @Schema(description = "订单类型 enums[SHOP,商城订单,1]")
    private Integer orderType;

    @Schema(description = "下单渠道 enums[PC,PC,1;APP,APP,2;MINI,小程序,3]")
    private Integer orderChannel;

    @Schema(description = "订单状态 enums[PENDING_PAY,待支付,1;PENDING_DELIVER,待发货,2;PENDING_RECEIVE,待收货,3;PENDING_EVALUATE,待评价,4;SUCCESS,交易成功,5]")
    private Integer orderStatus;

    @Schema(description = "支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]")
    private Integer payStatus;

    @Schema(description = "支付类型 enums[WECHAT,微信支付,1;ALIPAY,支付宝,2]")
    private String payType;

    @Schema(description = "应付金额")
    private Integer expectAmount;

    @Schema(description = "实付金额")
    private Integer actualAmount;

    @Schema(description = "运费金额")
    private Integer freightAmount;

    @Schema(description = "支付流水号")
    private String paySn;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "发货时间")
    private LocalDateTime deliverTime;

    @Schema(description = "确认收货时间")
    private LocalDateTime confirmTime;

    @Schema(description = "买家备注")
    private String buyerRemark;

    @Schema(description = "买家ID")
    private Long buyerId;

    @Schema(description = "卖家ID")
    private Long sellerId;

    @Schema(description = "物流公司")
    private String logisticsCompany;

    @Schema(description = "物流单号")
    private String logisticsCode;

}
