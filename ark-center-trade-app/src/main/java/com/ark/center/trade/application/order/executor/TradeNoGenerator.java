package com.ark.center.trade.application.order.executor;

import com.ark.center.trade.client.order.command.OrderCreateCmd;

public interface TradeNoGenerator {

    String generate(OrderCreateCmd orderCreateCmd);
}
