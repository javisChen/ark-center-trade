package com.ark.center.trade.application.pay.event;

import com.alibaba.fastjson2.JSON;
import com.ark.center.trade.client.pay.common.PayConst;
import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import com.ark.center.trade.client.pay.mq.PayOrderCreatedMessage;
import com.ark.component.mq.MsgBody;
import com.ark.component.mq.SendConfirm;
import com.ark.component.mq.SendResult;
import com.ark.component.mq.integration.MessageTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 角色API权限变更事件监听器
 * @author JC
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PayEventListener {

    private final MessageTemplate messageTemplate;

    @EventListener
    public void onApplicationEvent(@NotNull PayOrderCreatedEvent event) {

        log.info("PayOrder has been created: {}", event);

        PayOrderCreateDTO payOrder = event.getPayOrder();

        PayOrderCreatedMessage message = new PayOrderCreatedMessage();
        message.setBizTradeNo(payOrder.getBizTradeNo());
        message.setPayOrderId(payOrder.getPayOrderId());
        message.setPayTradeNo(payOrder.getPayTradeNo());
        message.setPayTypeCode(payOrder.getPayTypeCode());
        message.setPayTypeCode(payOrder.getPayTypeCode());

        messageTemplate.asyncSend(PayConst.MQ_TOPIC_PAY, PayConst.MQ_TAG_PAY_ORDER_CREATED_EVENT, MsgBody.of(message), new SendConfirm() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("Message [pay created] Published successfully. body = [{}]", JSON.toJSONString(message));
            }

            @Override
            public void onException(SendResult sendResult) {
                log.info("Message [pay created] Published failure. msgId = {}", sendResult.getMsgId(), sendResult.getThrowable());
            }
        });
    }

}
