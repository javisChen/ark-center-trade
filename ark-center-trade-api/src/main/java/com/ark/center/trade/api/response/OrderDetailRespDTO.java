package com.ark.center.trade.api.response;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderDetailRespDTO", description = "订单表")
public class OrderDetailRespDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "订单号", required = true)
    private String tradeNo;

    @ApiModelProperty(value = "订单类型 enums[SHOP,商城订单,1]", required = true)
    private Integer orderType;

    @ApiModelProperty(value = "下单渠道 enums[PC,PC,1;APP,APP,2;MINI,小程序,3]", required = true)
    private Integer orderChannel;

    @ApiModelProperty(value = "订单状态 enums[PENDING_PAY,待支付,1;PENDING_DELIVER,待发货,2;PENDING_RECEIVE,待收货,3;PENDING_EVALUATE,待评价,4;SUCCESS,交易成功,5]", required = true)
    private Integer orderStatus;

    @ApiModelProperty(value = "支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]", required = true)
    private Integer payStatus;

    @ApiModelProperty(value = "支付类型 enums[WECHAT,微信支付,1;ALIPAY,支付宝,2]", required = true)
    private String payType;

    @ApiModelProperty(value = "应付金额", required = true)
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额", required = true)
    private Integer actualAmount;

    @ApiModelProperty(value = "运费金额", required = true)
    private Integer freightAmount;

    @ApiModelProperty(value = "支付流水号", required = true)
    private String paySn;

    @ApiModelProperty(value = "支付时间", required = false)
    private LocalDateTime payTime;

    @ApiModelProperty(value = "发货时间", required = false)
    private LocalDateTime deliverTime;

    @ApiModelProperty(value = "确认收货时间", required = false)
    private LocalDateTime confirmTime;

    @ApiModelProperty(value = "买家备注", required = true)
    private String buyerRemark;

    @ApiModelProperty(value = "买家ID", required = true)
    private Long buyerId;

    @ApiModelProperty(value = "卖家ID", required = true)
    private Long sellerId;

    @ApiModelProperty(value = "物流公司", required = true)
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号", required = true)
    private String logisticsCode;

    @ApiModelProperty(value = "订单项", required = true)
    private List<OrderItemRespDTO> orderItems;

    @ApiModelProperty(value = "收货信息", required = true)
    private ReceiveRespDTO receiveInfo;

}
