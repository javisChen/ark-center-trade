package com.ark.center.trade.adapter.order.consumer;

import com.ark.center.pay.api.dto.mq.PayOrderCreatedMessage;
import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.application.order.mq.MQConst;
import com.ark.component.mq.MQType;
import com.ark.component.mq.core.annotations.MQMessageListener;
import com.ark.component.mq.core.processor.SimpleMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单支付单生成成功后通知
 */
@MQMessageListener(
        mq = MQType.ROCKET,
        consumerGroup = MQConst.CG_TAG_PAY_ORDER_CREATED,
        topic = MQConst.TOPIC_PAY,
        tags = MQConst.TAG_PAY_ORDER_CREATED
)
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderPayOrderCreatedConsumer extends SimpleMessageHandler<PayOrderCreatedMessage> {

    private final OrderAppService orderAppService;

    @Override
    protected void handleMessage(String msgId, String sendId, PayOrderCreatedMessage body, Object o) {
        log.info("支付单生成通知 -> msgId = {}, sendId = {}, body = {}", msgId, sendId, body);
        orderAppService.onPayOrderCreated(body);
    }
}
