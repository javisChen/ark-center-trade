package com.ark.center.trade.adapter.order.consumer;

import com.ark.center.pay.api.dto.mq.PayNotifyMessage;
import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.application.order.mq.MQConst;
import com.ark.component.mq.MQType;
import com.ark.component.mq.core.annotations.MQMessageListener;
import com.ark.component.mq.core.processor.SimpleMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单支付成功后通知
 */
@MQMessageListener(
        mq = MQType.ROCKET,
        consumerGroup = MQConst.CG_TAG_PAY_NOTIFY,
        topic = MQConst.TOPIC_PAY,
        tags = MQConst.TAG_PAY_NOTIFY
)
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderPayResultNotifyConsumer extends SimpleMessageHandler<PayNotifyMessage> {

    private final OrderAppService orderAppService;

    @Override
    protected void handleMessage(String msgId, String sendId, PayNotifyMessage body, Object o) {
        log.info("支付结果通知 -> msgId = {}, sendId = {}, body = {}", msgId, sendId, body);
        try {
            orderAppService.pay(body);
        } catch (Exception e) {
            log.error("处理支付结果通知失败", e);
            throw e;
        }
    }
}
