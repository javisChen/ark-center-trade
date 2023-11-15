package com.ark.center.trade.client.order.dto.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderBaseDTO {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tradeNo;

    @Schema(name = "订单类型 1-商城订单", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer orderType;

    @Schema(name = "下单渠道 1-PC 2-APP 3-小程序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer orderChannel;

    @Schema(name = "1-待支付 2-待发货 3-待收货 4-交易成功", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer orderStatus;

    @Schema(name = "1-待支付 2-支付中 3-支付成功 4-支付失败", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer payStatus;

    @Schema(name = "支付类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payTypeCode;

    @Schema(name = "支付单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payTradeNo;

    @Schema(name = "支付时间", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime payTime;

    @Schema(name = "发货时间", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime deliverTime;

    @Schema(name = "确认收货时间", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime confirmTime;

    @Schema(name = "买家备注", requiredMode = Schema.RequiredMode.REQUIRED)
    private String buyerRemark;

    @Schema(name = "买家ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long buyerId;

    @Schema(name = "卖家ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sellerId;

    @Schema(name = "物流公司", requiredMode = Schema.RequiredMode.REQUIRED)
    private String logisticsCompany;

    @Schema(name = "物流单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String logisticsCode;
    @Schema(name = "下单时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}