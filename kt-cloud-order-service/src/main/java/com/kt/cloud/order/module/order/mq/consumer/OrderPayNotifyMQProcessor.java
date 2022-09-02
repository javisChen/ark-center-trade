package com.kt.cloud.order.module.order.mq.consumer;

import com.kt.cloud.order.module.order.mq.MQConst;
import com.kt.cloud.order.module.order.service.OrderService;
import com.kt.cloud.pay.api.dto.mq.MQPayNotifyDTO;
import com.kt.component.mq.Message;
import com.kt.component.mq.core.annotations.MQMessageListener;
import com.kt.component.mq.core.processor.SimpleMQMessageProcessor;
import com.kt.component.mq.core.support.MQType;
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
public class OrderPayNotifyMQProcessor extends SimpleMQMessageProcessor<MQPayNotifyDTO> {

    private final OrderService orderService;

    public OrderPayNotifyMQProcessor(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected void handleMessage(String msgId, Message<MQPayNotifyDTO> message, Object o) {
        log.info("接收支付通知 -> msgId:{}, message:{}", msgId, message);

    }
}
