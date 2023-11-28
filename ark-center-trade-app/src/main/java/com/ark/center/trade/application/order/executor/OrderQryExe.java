package com.ark.center.trade.application.order.executor;

import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.infra.order.assembler.OrderBuildProfiles;
import com.ark.center.trade.infra.order.assembler.OrderBuilder;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderQryExe {

    private final OrderGateway orderGateway;

    private final OrderBuilder orderBuilder;

    public PageResponse<OrderDTO> queryPages(OrderQry pageQry) {
        PageResponse<Order> response = orderGateway.selectPages(pageQry);
        List<Order> records = response.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(new Page<>(response.getCurrent(), response.getSize()));
        }

        OrderBuildProfiles profiles = new OrderBuildProfiles();
        profiles.setWithOrderItems(pageQry.getWithOrderItems());
        profiles.setWithReceive(pageQry.getWithReceive());

        List<OrderDTO> orders = orderBuilder.build(records, profiles);
        return PageResponse.of(response.getCurrent(), response.getSize(), response.getTotal(), orders);
    }

    public OrderDTO queryDetails(Long orderId) {
        Order order = orderGateway.selectById(orderId);

        OrderBuildProfiles profiles = new OrderBuildProfiles();
        profiles.setWithOrderItems(true);
        profiles.setWithReceive(true);

        return orderBuilder.build(order, profiles);
    }

}
