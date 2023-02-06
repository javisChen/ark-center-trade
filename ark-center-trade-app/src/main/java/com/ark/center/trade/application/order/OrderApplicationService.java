package com.ark.center.trade.application.order;

import com.ark.center.trade.acl.sku.SkuServiceFacade;
import com.ark.center.trade.application.order.executor.OrderCreateCmdExe;
import com.ark.center.trade.application.order.service.OrderItemService;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.receive.service.ReceiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderCreateCmdExe orderCreateCmdExe;

    private final ReceiveService receiveService;
    private final OrderItemService orderItemService;
    private final SkuServiceFacade skuServiceFacade;

    @Transactional(rollbackFor = Throwable.class)
    public Long createOrder(OrderCreateCmd orderCreateCmd) {
        return orderCreateCmdExe.execute(orderCreateCmd);
    }

}
