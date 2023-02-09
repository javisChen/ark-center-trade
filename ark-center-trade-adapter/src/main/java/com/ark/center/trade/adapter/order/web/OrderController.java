package com.ark.center.trade.adapter.order.web;

import com.ark.center.trade.application.order.OrderAppService;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.command.OrderCreateCmd;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 订单服务
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Api(tags = "订单接口")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
public class OrderController extends BaseController {

    private final OrderAppService orderAppService;

    @ApiOperation(value = "订单-创建订单")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated OrderCreateCmd cmd) {
        return SingleResponse.ok(orderAppService.createOrder(cmd));
    }

    @ApiOperation(value = "订单-分页查询")
    @PostMapping("/page")
    public SingleResponse<PageResponse<OrderDTO>> pageList(@RequestBody @Validated OrderPageQry qry) {
        return SingleResponse.ok(orderAppService.getPageList(qry));
    }

    @ApiOperation(value = "订单-订单详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<OrderDTO> info(@RequestParam(value = "id")
                                               @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(orderAppService.getOrder(id));
    }


}
