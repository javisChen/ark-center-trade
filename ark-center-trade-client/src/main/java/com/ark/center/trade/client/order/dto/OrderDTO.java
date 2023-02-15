package com.ark.center.trade.client.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderDTO", description = "订单信息")
public class OrderDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "订单号", required = true)
    private String tradeNo;

    @ApiModelProperty(value = "订单类型 1-商城订单", required = true)
    private Integer orderType;

    @ApiModelProperty(value = "下单渠道 1-PC 2-APP 3-小程序", required = true)
    private Integer orderChannel;

    @ApiModelProperty(value = "1-待支付 2-待发货 3-待收货 4-交易成功", required = true)
    private Integer orderStatus;

    @ApiModelProperty(value = "1-待支付 2-支付中 3-支付成功 4-支付失败", required = true)
    private Integer payStatus;

    @ApiModelProperty(value = "1-微信支付 2-支付宝", required = true)
    private Integer payType;

    @ApiModelProperty(value = "应付金额（单位：分）", required = true)
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额（单位：分）", required = true)
    private Integer actualAmount;

    @ApiModelProperty(value = "运费金额（单位：分）", required = true)
    private Integer freightAmount;

    @ApiModelProperty(value = "支付流水号", required = true)
    private String payTradeNo;

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
    private List<OrderItemDTO> orderItems;

    @ApiModelProperty(value = "收货信息", required = true)
    private ReceiveDTO receive;

    @ApiModelProperty(value = "下单时间", required = true)
    private LocalDateTime gmtCreate;

}
