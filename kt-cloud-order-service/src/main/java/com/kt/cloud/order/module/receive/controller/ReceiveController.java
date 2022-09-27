package com.kt.cloud.order.module.receive.controller;

import com.kt.cloud.order.module.receive.dto.request.ReceiveUpdateReqDTO;
import com.kt.cloud.order.module.receive.dto.request.ReceivePageQueryReqDTO;
import com.kt.cloud.order.api.response.ReceiveRespDTO;
import com.kt.cloud.order.module.receive.service.ReceiveService;
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
 * 订单收货信息 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Api(tags = "订单收货信息")
@Validated
@RestController
@RequestMapping("/v1/receive")
public class ReceiveController extends BaseController {

    private final ReceiveService receiveService;

    public ReceiveController(ReceiveService receiveService) {
        this.receiveService = receiveService;
    }

    @ApiOperation(value = "创建订单收货信息")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated ReceiveUpdateReqDTO reqDTO) {
        return SingleResponse.ok(receiveService.createReceive(reqDTO));
    }

    @ApiOperation(value = "修改订单收货信息")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated(ValidateGroup.Update.class) ReceiveUpdateReqDTO reqDTO) {
        return SingleResponse.ok(receiveService.updateReceive(reqDTO));
    }

    @ApiOperation(value = "查询订单收货信息分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<ReceiveRespDTO>> pageList(@RequestBody @Validated ReceivePageQueryReqDTO queryDTO) {
        return SingleResponse.ok(receiveService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询订单收货信息详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<ReceiveRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(receiveService.getReceiveInfo(id));
    }


}
