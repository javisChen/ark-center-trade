package com.ark.center.trade.infra.pay.service;

import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import com.ark.center.trade.infra.pay.PayOrderDO;
import com.ark.center.trade.infra.pay.db.PayOrderMapper;
import com.ark.component.exception.BizException;
import com.ark.component.mq.integration.MessageTemplate;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付订单表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PayOrderService extends ServiceImpl<PayOrderMapper, PayOrderDO> implements IService<PayOrderDO> {

    private final MessageTemplate messageTemplate;
    private final ApplicationEventPublisher eventPublisher;

//    public PageResponse<PayOrderCreateDTO> getPageList(PayOrderPageQueryReqDTO queryDTO) {
//        IPage<PayOrderCreateDTO> page = lambdaQuery()
//                .orderByDesc(BaseEntity::getGmtCreate)
//                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
//                .convert(item -> BeanConvertor.copy(item, PayOrderCreateDTO.class));
//        return BeanConvertor.copyPage(page, PayOrderCreateDTO.class);
//    }

//    public Map<String, Object> notify(JSONObject reqDTO) {
//        log.info("支付结果通知：入参 = {}", reqDTO);
//        String payTradeNo = reqDTO.getString("payTradeNo");
//        String bizTradeNo = reqDTO.getString("bizTradeNo");
//        Integer status = reqDTO.getInteger("status");
//        PayOrderDO payOrder = getByTradeNo(payTradeNo);
//        Assert.notNull(payOrder, () -> ExceptionFactory.userException("支付单不存在"));
//
//        Long payOrderId = payOrder.getId();
//
//        // 推送支付结果到MQ
//        PayNotifyMessage dto = new PayNotifyMessage();
//        dto.setOutTradeNo(payOrder.getOutTradeNo());
//        dto.setBizTradeNo(payOrder.getBizTradeNo());
//        dto.setPayOrderId(payOrderId);
//        dto.setBizTradeNo(bizTradeNo);
//        dto.setPayTradeNo(payOrder.getPayTradeNo());
//        dto.setResult(1);
//
//        updatePayOrderStatus(payOrderId, status);
//        messageTemplate.asyncSend(MQConst.TOPIC_PAY, MQConst.TAG_PAY_NOTIFY, MsgBody.of(dto), new SendConfirm() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                log.info("Publish pay result successfully. msg = {}, body = {}", sendResult.getMsgId(), JSON.toJSONString(dto));
//            }
//
//            @Override
//            public void onException(SendResult sendResult) {
//                log.error("Publish pay result failure. msgId = {}", sendResult.getMsgId(), sendResult.getThrowable());
//            }
//        });
//        return Maps.newHashMap();
//    }

    private PayOrderDO getByTradeNo(String payTradeNo) {
        return lambdaQuery()
                .eq(PayOrderDO::getPayTradeNo, payTradeNo)
                .last("limit 1")
                .one();
    }

    private void updatePayOrderStatus(Long payOrderId, Integer status) {
        lambdaUpdate()
                .eq(BaseEntity::getId, payOrderId)
                .set(PayOrderDO::getStatus, PayOrderDO.Status.PAY_SUCCESS.getValue())
                .update();
    }

    public PayOrderCreateDTO getPayOrderInfo(Long payOrderId) {
        PayOrderDO entity = getById(payOrderId);
        return BeanConvertor.copy(entity, PayOrderCreateDTO.class);
    }

    public Integer getPayOrderStatus(Long payOrderId) {
        PayOrderDO payOrderDO = lambdaQuery()
                .select(BaseEntity::getId, PayOrderDO::getStatus)
                .eq(BaseEntity::getId, payOrderId)
                .one();
        if (payOrderDO == null) {
            throw new BizException("支付单号不存在");
        }
        return payOrderDO.getStatus();
    }
}
