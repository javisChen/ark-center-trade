package com.ark.center.trade.application.order.executor;

import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.component.context.core.ServiceContext;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 默认交易单号生成器
 * 默认19位，年月日+用户id后4位
 *
 */
public class DefaultTradeNoGenerator implements TradeNoGenerator {

    @Override
    public String generate(OrderCreateCmd orderCreateCmd) {
        Long userId = ServiceContext.getCurrentUser().getUserId();
        String lastFour = StringUtils.substringAfterLast(String.valueOf(userId), 5);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssS")) + lastFour;
    }

}
