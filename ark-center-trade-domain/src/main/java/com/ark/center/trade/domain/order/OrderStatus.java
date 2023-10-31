package com.ark.center.trade.domain.order;

import com.ark.center.trade.domain.order.model.Order;
import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus implements BasicEnums {

    COMPLETED(10, "已完成"),

    WAIT_DELIVER(2, "待发货"),

    WAIT_EVALUATE(4, "待评价"),

    WAIT_PAY(1, "待支付"),

    WAIT_RECEIVE(3, "待收货");;
    private final Integer value;
    private final String text;

    public static OrderStatus getByValue(Integer value) {
        return EnumUtils.getByValue(values(), value);
    }

    public static String getText(Integer value) {
        return EnumUtils.getTextByValue(values(), value);
    }
}
