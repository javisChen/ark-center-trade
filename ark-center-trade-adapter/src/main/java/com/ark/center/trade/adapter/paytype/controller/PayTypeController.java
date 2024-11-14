package com.ark.center.trade.adapter.paytype.controller;

import com.ark.center.trade.infra.pay.service.PayTypeService;
import com.ark.center.trade.client.pay.command.PayTypeUpdateCommand;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.validator.ValidateGroup;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 支付类别表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Schema(name = "支付类别表", description = "支付类别表")
@Validated
@RestController
@RequestMapping("/v1/pay-type")
public class PayTypeController extends BaseController {

    private final PayTypeService payTypeService;

    public PayTypeController(PayTypeService payTypeService) {
        this.payTypeService = payTypeService;
    }

    @Operation(summary = "创建支付类别表")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated PayTypeUpdateCommand reqDTO) {
        return SingleResponse.ok(payTypeService.createPayType(reqDTO));
    }

    @Operation(summary = "修改支付类别表")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated(ValidateGroup.Update.class) PayTypeUpdateCommand reqDTO) {
        return SingleResponse.ok(payTypeService.updatePayType(reqDTO));
    }

    @Operation(summary = "查询支付类别表分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<PayTypeRespDTO>> pageList(@RequestBody @Validated PayTypePageQueryReqDTO queryDTO) {
        return SingleResponse.ok(payTypeService.getPageList(queryDTO));
    }

    @Operation(summary = "查询支付类别表详情")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/info")
    public SingleResponse<PayTypeRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(payTypeService.getPayTypeInfo(id));
    }


}
