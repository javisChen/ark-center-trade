package com.ark.center.trade.domain.order;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
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
@TableName("od_order")
public class Order extends BaseEntity {


    /**
     * 订单号
     */
    @TableField("`trade_no`")
    private String tradeNo;

    /**
     * 订单类型 enums[SHOP,商城订单,1]
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 下单渠道 enums[PC,PC,1;APP,APP,2;MINI,小程序,3]
     */
    @TableField("order_channel")
    private Integer orderChannel;

    /**
     * 订单状态 enums[PENDING_PAY,待支付,1;PENDING_DELIVER,待发货,2;PENDING_RECEIVE,待收货,3;PENDING_EVALUATE,待评价,4;SUCCESS,交易成功,5]
     */
    @TableField("order_status")
    private Integer orderStatus;

    /**
     * 支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]
     */
    @TableField("pay_status")
    private Integer payStatus;

    /**
     * 支付类型 enums[WECHAT,微信支付,1;ALIPAY,支付宝,2]
     */
    @TableField("pay_type_code")
    private String payTypeCode;

    /**
     * 应付金额
     */
    @TableField("expect_amount")
    private Integer expectAmount;

    /**
     * 实付金额
     */
    @TableField("actual_amount")
    private Integer actualAmount;

    /**
     * 运费金额
     */
    @TableField("freight_amount")
    private Integer freightAmount;

    /**
     * 支付流水号
     */
    @TableField("pay_trade_no")
    private String payTradeNo;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    @TableField("deliver_time")
    private LocalDateTime deliverTime;

    /**
     * 确认收货时间
     */
    @TableField("confirm_time")
    private LocalDateTime confirmTime;

    /**
     * 买家备注
     */
    @TableField("buyer_remark")
    private String buyerRemark;

    /**
     * 买家ID
     */
    @TableField("buyer_id")
    private Long buyerId;

    /**
     * 买家名称
     */
    @TableField("buyer_name")
    private String buyerName;

    /**
     * 卖家ID
     */
    @TableField("seller_id")
    private Long sellerId;

    /**
     * 物流公司
     */
    @TableField("logistics_company")
    private String logisticsCompany;

    /**
     * 物流单号
     */
    @TableField("logistics_code")
    private String logisticsCode;
}
