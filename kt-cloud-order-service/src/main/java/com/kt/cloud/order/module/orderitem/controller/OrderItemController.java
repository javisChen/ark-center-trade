package com.kt.cloud.order.module.orderitem.controller;

import com.kt.cloud.order.module.orderitem.dto.request.OrderItemUpdateReqDTO;
import com.kt.cloud.order.module.orderitem.dto.request.OrderItemPageQueryReqDTO;
import com.kt.cloud.order.module.orderitem.dto.response.OrderItemRespDTO;
import com.kt.cloud.order.module.orderitem.service.OrderItemService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.validator.ValidateGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import com.kt.component.web.base.BaseController;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Api(tags = "订单明细表")
@Validated
@RestController
@RequestMapping("/v1/order-item")
public class OrderItemController extends BaseController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @ApiOperation(value = "创建订单明细表")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated OrderItemUpdateReqDTO reqDTO) {
        return SingleResponse.ok(orderItemService.createOrderItem(reqDTO));
    }

    @ApiOperation(value = "修改订单明细表")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated(ValidateGroup.Update.class) OrderItemUpdateReqDTO reqDTO) {
        return SingleResponse.ok(orderItemService.updateOrderItem(reqDTO));
    }

    @ApiOperation(value = "查询订单明细表分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<OrderItemRespDTO>> pageList(@RequestBody @Validated OrderItemPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(orderItemService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询订单明细表详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<OrderItemRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(orderItemService.getOrderItemInfo(id));
    }


}
