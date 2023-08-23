package com.ark.center.trade.client.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id", required = true)
    private Long id;

    @Schema(description = "订单号", required = true)
    private String tradeNo;

    @Schema(description = "订单类型 1-商城订单", required = true)
    private Integer orderType;

    @Schema(description = "下单渠道 1-PC 2-APP 3-小程序", required = true)
    private Integer orderChannel;

    @Schema(description = "1-待支付 2-待发货 3-待收货 4-交易成功", required = true)
    private Integer orderStatus;

    @Schema(description = "1-待支付 2-支付中 3-支付成功 4-支付失败", required = true)
    private Integer payStatus;

    @Schema(description = "1-微信支付 2-支付宝", required = true)
    private Integer payType;

    @Schema(description = "应付金额（单位：分）", required = true)
    private Integer expectAmount;

    @Schema(description = "实付金额（单位：分）", required = true)
    private Integer actualAmount;

    @Schema(description = "运费金额（单位：分）", required = true)
    private Integer freightAmount;

    @Schema(description = "支付流水号", required = true)
    private String payTradeNo;

    @Schema(description = "支付时间", required = false)
    private LocalDateTime payTime;

    @Schema(description = "发货时间", required = false)
    private LocalDateTime deliverTime;

    @Schema(description = "确认收货时间", required = false)
    private LocalDateTime confirmTime;

    @Schema(description = "买家备注", required = true)
    private String buyerRemark;

    @Schema(description = "买家ID", required = true)
    private Long buyerId;

    @Schema(description = "卖家ID", required = true)
    private Long sellerId;

    @Schema(description = "物流公司", required = true)
    private String logisticsCompany;

    @Schema(description = "物流单号", required = true)
    private String logisticsCode;

    @Schema(description = "订单项", required = true)
    private List<OrderItemDTO> orderItems;

    @Schema(description = "收货信息", required = true)
    private ReceiveDTO receive;

    @Schema(description = "下单时间", required = true)
    private LocalDateTime gmtCreate;

}
