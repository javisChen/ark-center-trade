package com.ark.center.trade.application.pay;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ark.center.pay.api.dto.mq.PayNotifyMessage;
import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.application.order.mq.MQConst;
import com.ark.center.trade.application.pay.event.PayOrderCreatedEvent;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderDetailsQuery;
import com.ark.center.trade.client.pay.command.PayOrderCreateCommand;
import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import com.ark.center.trade.infra.order.service.OrderService;
import com.ark.center.trade.infra.pay.PayOrderDO;
import com.ark.center.trade.infra.pay.service.PayOrderService;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.mq.MsgBody;
import com.ark.component.mq.SendConfirm;
import com.ark.component.mq.SendResult;
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

    public PayOrderCreateDTO createPayOrder(PayOrderCreateCommand command) {

        log.info("生成支付单,入参：{}", command);
        String payTypeCode = command.getPayTypeCode();
        Integer payTypeId = command.getPayTypeId();
        String bizTradeNo = command.getBizTradeNo();
        String payTradeNo = IdUtil.getSnowflakeNextIdStr();

        OrderDetailsQuery query = new OrderDetailsQuery();
        query.setTradeNo(bizTradeNo);
        OrderDTO order = orderQryExe.queryDetails(query);
        Assert.notNull(order, ExceptionFactory.userExceptionSupplier("交易单" + bizTradeNo + "不存在"));
        log.info("交易单信息：{}", order.getOrderBase());

        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setBizTradeNo(bizTradeNo);
        payOrderDO.setPayTradeNo(payTradeNo);
        payOrderDO.setPayTypeCode(payTypeCode);
        payOrderDO.setPayTypeId(payTypeId);
        payOrderDO.setAmount(order.getOrderAmount().getActualAmount());
        payOrderDO.setDescription(command.getDescription());
        payOrderDO.setStatus(PayOrderDO.Status.PENDING_PAY.getValue());
        payOrderService.save(payOrderDO);

        PayOrderCreateDTO dto = new PayOrderCreateDTO();
        Long payOrderId = payOrderDO.getId();
        dto.setPayOrderId(payOrderId);
        dto.setPayTradeNo(payTradeNo);
        dto.setBizTradeNo(bizTradeNo);
        dto.setPayTypeCode(payTypeCode);

        // 发布事件
        eventPublisher.publishEvent(new PayOrderCreatedEvent(this, dto));
        return dto;
    }

    public Map<String, Object> handleNotify(JSONObject reqDTO) {
        log.info("支付结果通知：入参 = {}", reqDTO);
        String payTradeNo = reqDTO.getString("payTradeNo");
        String bizTradeNo = reqDTO.getString("bizTradeNo");
        Integer status = reqDTO.getInteger("status");
        PayOrderDO payOrder = getByTradeNo(payTradeNo);
        Assert.notNull(payOrder, () -> ExceptionFactory.userException("支付单不存在"));

        Long payOrderId = payOrder.getId();

        // 推送支付结果到MQ
        PayNotifyMessage dto = new PayNotifyMessage();
        dto.setOutTradeNo(payOrder.getOutTradeNo());
        dto.setBizTradeNo(payOrder.getBizTradeNo());
        dto.setPayOrderId(payOrderId);
        dto.setBizTradeNo(bizTradeNo);
        dto.setPayTradeNo(payOrder.getPayTradeNo());
        dto.setResult(1);

        updatePayOrderStatus(payOrderId, status);
        messageTemplate.asyncSend(MQConst.TOPIC_PAY, MQConst.TAG_PAY_NOTIFY, MsgBody.of(dto), new SendConfirm() {
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
