package com.ark.center.trade.application.order;

import com.ark.center.trade.application.order.executor.OrderCreateCmdExe;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderCreateCmdExe orderCreateCmdExe;

    @Transactional(rollbackFor = Throwable.class)
    public Long createOrder(OrderCreateCmd orderCreateCmd) {
        return orderCreateCmdExe.execute(orderCreateCmd);
    }

}
