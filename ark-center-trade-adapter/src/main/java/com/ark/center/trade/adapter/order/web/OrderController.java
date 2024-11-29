package com.ark.center.trade.adapter.order.web;

import com.ark.center.trade.application.order.OrderCommandHandler;
import com.ark.center.trade.application.order.OrderQueryService;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderDeliverCmd;
import com.ark.center.trade.client.order.command.OrderReceiveCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderDetailsQuery;
import com.ark.center.trade.client.order.query.OrderQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
public class OrderController extends BaseController {

    private final OrderQueryService orderQueryService;
    private final OrderCommandHandler orderCommandHandler;

    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated OrderCreateCmd cmd) {
        return SingleResponse.ok(orderCommandHandler.createOrder(cmd));
    }

    @Operation(summary = "订单发货")
    @PostMapping("/deliver")
    public ServerResponse deliver(@RequestBody @Validated OrderDeliverCmd cmd) {
        orderCommandHandler.deliver(cmd);
        return ServerResponse.ok();
    }
    @Operation(summary = "确认收货")
    @PostMapping("/receive")
    public ServerResponse receive(@RequestBody @Validated OrderReceiveCmd cmd) {
        orderCommandHandler.receive(cmd);
        return ServerResponse.ok();
    }

    @Operation(summary = "查询订单列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<OrderDTO>> queryPages(@RequestBody @Validated OrderQry qry) {
        return SingleResponse.ok(orderQueryService.queryPages(qry));
    }

    @Operation(summary = "查询订单详情")
    @GetMapping("/details")
    public SingleResponse<OrderDTO> details(OrderDetailsQuery qry) {
        return SingleResponse.ok(orderQueryService.queryDetails(qry));
    }


}
