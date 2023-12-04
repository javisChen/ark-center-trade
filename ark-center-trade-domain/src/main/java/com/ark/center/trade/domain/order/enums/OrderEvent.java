package com.ark.center.trade.domain.order.enums;

public enum OrderEvent {

    CREATE("下单"),

    PAY("支付"),

    DELIVER("发货"),

    RECEIVE("收货"),

    EVALUATE("评价");

    private final String name;

    OrderEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
