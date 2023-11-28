package com.ark.center.trade.adapter.order.web;

import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.UserOrderPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户订单管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user/order")
public class UserOrderController extends BaseController {

    private final OrderAppService orderAppService;

    @Operation(summary = "查询订单列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<OrderDTO>> queryPages(@RequestBody @Validated UserOrderPageQry qry) {
        return SingleResponse.ok(orderAppService.queryUserOrderPages(qry));
    }

    @Operation(summary = "查询订单详情")
    @GetMapping("/details")
    public SingleResponse<OrderDTO> details(@RequestParam(value = "id")
                                                   @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(orderAppService.queryDetails(id));
    }


}
