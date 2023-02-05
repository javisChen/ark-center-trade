package com.ark.center.trade.adapter.order.web;

import com.ark.center.trade.application.order.dto.request.OrderCreateDTO;
import com.ark.center.trade.application.order.dto.request.OrderPageQueryReqDTO;
import com.ark.center.trade.client.order.response.OrderDetailRespDTO;
import com.ark.center.trade.application.order.service.OrderService;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.validator.ValidateGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import com.ark.component.web.base.BaseController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Api(tags = "订单接口")
@Validated
@RestController
@RequestMapping("/v1/order")
public class OrderController extends BaseController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "订单-创建订单")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated OrderCreateDTO reqDTO) {
        return SingleResponse.ok(orderService.createOrder(reqDTO));
    }

    @ApiOperation(value = "修改订单")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated(ValidateGroup.Update.class) OrderCreateDTO reqDTO) {
        return SingleResponse.ok(orderService.updateOrder(reqDTO));
    }

    @ApiOperation(value = "查询订单表分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<OrderDetailRespDTO>> pageList(@RequestBody @Validated OrderPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(orderService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询订单表详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<OrderDetailRespDTO> info(@RequestParam(value = "id") @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(orderService.getOrderInfo(id));
    }


}
