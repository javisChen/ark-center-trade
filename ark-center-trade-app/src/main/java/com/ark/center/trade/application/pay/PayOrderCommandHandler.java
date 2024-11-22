package com.ark.center.trade.application.pay;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.client.pay.common.PayConst;
import com.ark.center.trade.client.pay.mq.PayOrderChangedEventDTO;
import com.ark.center.trade.infra.order.service.OrderService;
import com.ark.center.trade.infra.pay.PayOrderDO;
import com.ark.center.trade.infra.pay.service.PayOrderService;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.mq.MsgBody;
import com.ark.component.mq.SendConfirm;
import com.ark.component.mq.SendResult;
import com.ark.component.mq.integration.MessageTemplate;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class PayOrderCommandHandler {

    private final OrderQryExe orderQryExe;
    private final OrderAppService orderAppService;
    private final OrderService orderService;
    private final PayOrderService payOrderService;
    private final ApplicationEventPublisher eventPublisher;
    private final MessageTemplate messageTemplate;

    public Map<String, Object> handleNotify(JSONObject reqDTO) {
        log.info("支付结果通知：入参 = {}", reqDTO);
        String payTradeNo = reqDTO.getString("payTradeNo");
        String bizTradeNo = reqDTO.getString("bizTradeNo");
        Integer status = reqDTO.getInteger("status");
        PayOrderDO payOrder = payOrderService.getByTradeNo(payTradeNo);
        Assert.notNull(payOrder, () -> ExceptionFactory.userException("支付单不存在"));

        Long payOrderId = payOrder.getId();

        // 推送支付结果到MQ
        PayOrderChangedEventDTO dto = new PayOrderChangedEventDTO();
        dto.setBizTradeNo(payOrder.getBizTradeNo());
        dto.setPayTradeNo(payOrder.getPayTradeNo());
        dto.setResult(1);

        payOrderService.updatePayOrderStatus(payOrderId, status);

        messageTemplate.asyncSend(PayConst.TOPIC_PAY, PayConst.TAG_PAY_ORDER_STATUS_CHANGED, MsgBody.of(dto), new SendConfirm() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("Publish pay result successfully. msg = {}, body = {}", sendResult.getMsgId(), JSON.toJSONString(dto));
            }

            @Override
            public void onException(SendResult sendResult) {
                log.error("Publish pay result failure. msgId = {}", sendResult.getMsgId(), sendResult.getThrowable());
            }
        });
        return Maps.newHashMap();
    }



}
