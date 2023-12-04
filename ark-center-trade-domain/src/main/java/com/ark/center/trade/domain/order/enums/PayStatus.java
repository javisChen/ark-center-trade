package com.ark.center.trade.domain.order.enums;

import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
