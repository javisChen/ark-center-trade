package com.ark.center.trade.application.order.mq;

public interface MQConst {


    String TOPIC_PAY = "topic_pay";

    /**
     * 支付结果通知
     */

    String TAG_PAY_NOTIFY = "tag_pay_notify";

    String CG_TAG_PAY_NOTIFY = "cg_pay_pay_notify";

    /**
     * 支付单生成通知
     */

    String TAG_PAY_ORDER_CREATED = "tag_pay_order_created";

    String CG_TAG_PAY_ORDER_CREATED = "cg_pay_tag_pay_order_created";

}
