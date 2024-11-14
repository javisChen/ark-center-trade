package com.ark.center.trade.infra.pay;

import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
/**
 * <p>
 * 支付订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_order")
public class PayOrderDO extends BaseEntity {


    /**
     * 业务订单号
     */
    @TableField("biz_trade_no")
    private String bizTradeNo;

    /**
     * 支付订单号
     */
    @TableField("pay_trade_no")
    private String payTradeNo;

    /**
     * 第三方平台交易单号
     */
    @TableField("out_trade_no")
    private String outTradeNo;

    /**
     * 支付类型编码
     */
    @TableField("pay_type_code")
    private String payTypeCode;


    /**
     * 支付类型id
     */
    @TableField("pay_type_id")
    private Integer payTypeId;

    /**
     * 支付金额
     */
    @TableField("amount")
    private Integer amount;

    /**
     * 支付单描述信息
     */
    @TableField("`description`")
    private String description;

    /**
     * 支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]
     */
    @TableField("`status`")
    private Integer status;

    /**
     * 最后一次支付结果通知时间
     */
    @TableField("last_notify_time")
    private LocalDateTime lastNotifyTime;

    @Getter
    @AllArgsConstructor
    public enum Status implements BasicEnums {
        PENDING_PAY(1, "待支付"),
        PAYING(2, "支付中"),
        PAY_SUCCESS(3, "支付成功"),
        PAY_FAIL(4, "支付失败");
        private final Integer value;
        private final String text;

        public static Status getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
}
