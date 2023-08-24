package com.ark.center.trade.adapter.order.web;

import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.info.OrderInfoDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
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

    @Operation(summary = "订单-创建订单")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated OrderCreateCmd cmd) {
        return SingleResponse.ok(orderAppService.createOrder(cmd));
    }

    @Operation(summary = "订单-分页查询")
    @PostMapping("/page")
    public SingleResponse<PageResponse<OrderDTO>> pageList(@RequestBody @Validated OrderPageQry qry) {
        return SingleResponse.ok(orderAppService.getPageList(qry));
    }

    @Operation(summary = "订单-订单详情")
    @GetMapping("/info")
    public SingleResponse<OrderInfoDTO> info(@RequestParam(value = "id")
                                         @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(orderAppService.getOrder(id));
    }


}
