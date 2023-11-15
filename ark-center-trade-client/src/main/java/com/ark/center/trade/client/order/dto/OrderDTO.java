package com.ark.center.trade.client.order.dto;

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
@Schema(name = "OrderDTO", description = "订单信息")
public class OrderDTO implements Serializable {

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

    @Schema(name = "应付金额（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer expectAmount;

    @Schema(name = "实付金额（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer actualAmount;

    @Schema(name = "运费金额（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer freightAmount;

    @Schema(name = "支付流水号", requiredMode = Schema.RequiredMode.REQUIRED)
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

    @Schema(name = "订单项", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OrderItemDTO> orderItems;

    @Schema(name = "收货信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private ReceiveDTO receive;

    @Schema(name = "下单时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime gmtCreate;

}
