package com.ark.center.trade.client.order.dto.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderBaseDTO {

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
    @Schema(description = "下单时间", required = true)
    private LocalDateTime createTime;

}