package com.ark.center.trade.application.pay;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.application.pay.event.PayOrderCreatedEvent;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderDetailsQuery;
import com.ark.center.trade.client.pay.command.PayOrderCreateCommand;
import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.infra.pay.PayOrderDO;
import com.ark.center.trade.infra.pay.service.PayOrderService;
import com.ark.component.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class PayCommandHandler {

    private final OrderQryExe orderQryExe;
    private final OrderAppService orderAppService;
    private final OrderGateway orderGateway;
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
        Assert.notNull(order, () -> ExceptionFactory.userException("交易单" + bizTradeNo + "不存在"));
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
        return null;
    }
}
