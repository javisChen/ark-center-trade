package com.ark.center.trade.adapter.order.consumer;

import com.ark.center.pay.api.dto.mq.PayNotifyMessage;
import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.application.order.mq.MQConst;
import com.ark.component.mq.core.annotations.MQMessageListener;
import com.ark.component.mq.core.processor.SimpleMQMessageProcessor;
import com.ark.component.mq.core.support.MQType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@MQMessageListener(
        mq = MQType.ROCKET,
        consumerGroup = MQConst.CG_TAG_PAY_NOTIFY,
        topic = MQConst.TOPIC_PAY,
        tags = MQConst.TAG_PAY_NOTIFY
)
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderPayNotifyMQConsumer extends SimpleMQMessageProcessor<PayNotifyMessage> {

    private final OrderAppService orderAppService;

    @Override
    protected void handleMessage(String msgId, String sendId, PayNotifyMessage body, Object o) {
        log.info("接收支付通知 -> msgId = {}, sendId = {}, body = {}", msgId, sendId, body);
        orderAppService.updateOrderOnPaySuccess(body);
    }
}
