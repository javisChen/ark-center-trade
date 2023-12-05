package com.ark.center.trade.adapter.order.web;

import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.command.OrderDeliverCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderDetailsQry;
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

    private final OrderAppService orderAppService;

    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated OrderCreateCmd cmd) {
        return SingleResponse.ok(orderAppService.createOrder(cmd));
    }

    @Operation(summary = "订单发货")
    @PostMapping("/deliver")
    public ServerResponse deliver(@RequestBody @Validated OrderDeliverCmd cmd) {
        orderAppService.deliver(cmd);
        return ServerResponse.ok();
    }

    @Operation(summary = "查询订单列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<OrderDTO>> queryPages(@RequestBody @Validated OrderQry qry) {
        return SingleResponse.ok(orderAppService.queryPages(qry));
    }

    @Operation(summary = "查询订单详情")
    @GetMapping("/details")
    public SingleResponse<OrderDTO> details(OrderDetailsQry qry) {
        return SingleResponse.ok(orderAppService.queryDetails(qry));
    }


}
