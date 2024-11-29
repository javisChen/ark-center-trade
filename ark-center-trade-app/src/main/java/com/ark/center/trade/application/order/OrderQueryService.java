package com.ark.center.trade.application.order;

import com.ark.center.trade.application.order.executor.OrderQryExe;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderDetailsQuery;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.center.trade.client.order.query.UserOrderPageQry;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderQueryService {

    private final OrderQryExe orderQryExe;

    /**
     * 查询订单列表
     */
    public PageResponse<OrderDTO> queryPages(OrderQry qry) {
        qry.setWithOrderItems(false);
        qry.setWithReceive(false);
        return orderQryExe.queryPages(qry);
    }

    /**
     * 查询用户的订单列表
     */
    public PageResponse<OrderDTO> queryUserOrderPages(UserOrderPageQry qry) {
        OrderQry orderQry = new OrderQry();
        orderQry.setBuyerId(ServiceContext.getCurrentUser().getUserId());
        orderQry.setOrderStatus(qry.getOrderStatus());
        orderQry.setPayStatus(qry.getPayStatus());
        orderQry.setTradeNo(qry.getTradeNo());
        orderQry.setCurrent(qry.getCurrent());
        orderQry.setSize(qry.getSize());
        orderQry.setWithOrderItems(true);
        return orderQryExe.queryPages(orderQry);
    }

    /**
     * 查询订单详情
     */
    public OrderDTO queryDetails(OrderDetailsQuery qry) {
        return orderQryExe.queryDetails(qry);
    }

}
