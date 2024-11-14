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
/**
 * <p>
 * 支付结果通知记录
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_notify_record")
public class PayNotifyRecordDO extends BaseEntity {


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
     * 通知请求体
     */
    @TableField("req_body")
    private String reqBody;

    /**
     * 支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]
     */
    @TableField("`status`")
    private Integer status;

    @Getter
    @AllArgsConstructor
    public enum Status implements BasicEnums {
        PENDING_PAY(1, "待支付"),
            PAYING(2, "支付中"),
            PAY_SUCCESS(3, "支付成功"),
            PAY_FAIL(4, "支付失败"),
    ;
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
