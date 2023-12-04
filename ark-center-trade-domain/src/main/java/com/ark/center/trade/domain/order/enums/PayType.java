package com.ark.center.trade.domain.order.enums;

import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
