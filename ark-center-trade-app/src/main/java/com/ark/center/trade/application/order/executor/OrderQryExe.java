package com.ark.center.trade.application.order.executor;

import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderDetailsQry;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.domain.order.Order;
import com.ark.center.trade.domain.order.gateway.OrderGateway;
import com.ark.center.trade.infra.order.builder.OrderBuildProfiles;
import com.ark.center.trade.infra.order.builder.OrderBuilder;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderQryExe {

    private final OrderGateway orderGateway;

    private final OrderBuilder orderBuilder;

    public PageResponse<OrderDTO> queryPages(OrderQry pageQry) {
        IPage<Order> response = orderGateway.selectPages(pageQry);
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

    public OrderDTO queryDetails(OrderDetailsQry qry) {
        Order order;
        if (qry.getId() != null) {
            order = orderGateway.selectById(qry.getId());
        } else if (StringUtils.isNotBlank(qry.getTradeNo())){
            order = orderGateway.selectByTradeNo(qry.getTradeNo());
        } else {
            throw ExceptionFactory.userException("id和tradeNo至少传入一个");
        }
        return buildDTO(order);
    }

    private OrderDTO buildDTO(Order order) {
        OrderBuildProfiles profiles = new OrderBuildProfiles();
        profiles.setWithOrderItems(true);
        profiles.setWithReceive(true);
        return orderBuilder.build(order, profiles);
    }

}
