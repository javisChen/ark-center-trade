package com.ark.center.trade.client.pay.common;

public interface PayConst {

    /**
     * MQ定义
     */
    String MQ_TOPIC_PAY = "topic_pay";

    String MQ_TAG_PAY_NOTIFY = "tag_pay_notify";

    String MQ_CG_TAG_PAY_NOTIFY = "cg_pay_pay_notify";

    /**
     * 支付单生成通知
     */
    String MQ_TAG_PAY_ORDER_CREATED_EVENT = "tag_pay_order_created";


}
