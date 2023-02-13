package com.ark.center.trade.domain.order.model.vo;

import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 订单支付信息
 */
@Data
public class OrderPay {

    /**
     * 支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]
     */
    private PayStatus payStatus;

    /**
     * 支付类型 enums[WECHAT,微信支付,1;ALIPAY,支付宝,2]
     */
    private PayType payType;

    /**
     * 支付流水号
     */
    private String payTradeNo;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;


    @Getter
    @AllArgsConstructor
    public enum PayStatus implements BasicEnums {
        PENDING_PAY(1, "待支付"),
        PAYING(2, "支付中"),
        PAY_SUCCESS(3, "支付成功"),
        PAY_FAIL(4, "支付失败"),
        ;
        private final Integer value;
        private final String text;

        public static PayStatus getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum PayType implements BasicEnums {
        WECHAT(1, "微信支付"),
        ALIPAY(2, "支付宝"),
        ;
        private final Integer value;
        private final String text;

        public static PayType getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }

}
