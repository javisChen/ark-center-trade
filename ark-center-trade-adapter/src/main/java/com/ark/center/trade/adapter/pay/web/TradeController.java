package com.ark.center.trade.adapter.pay.web;

import com.ark.center.trade.application.pay.PayCommandHandler;
import com.ark.center.trade.application.pay.PayQueryService;
import com.ark.center.trade.client.pay.command.PayOrderCreateCommand;
import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import com.ark.center.trade.client.pay.query.PayOrderPageQuery;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 支付订单表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Schema(name = "统一支付交易", description = "统一支付交易")
@Validated
@RestController
@RequestMapping("/v1/trade")
@RequiredArgsConstructor
public class TradeController extends BaseController {

    private final PayCommandHandler payCommandHandler;
    private final PayQueryService payQueryService;

    @Operation(summary = "支付")
    @PostMapping("/pay")
    public SingleResponse<PayOrderCreateDTO> pay(@RequestBody @Validated PayOrderCreateCommand command) {
        return SingleResponse.ok(payCommandHandler.pay(command));
    }

    @Operation(summary = "退款")
    @PostMapping("/refund")
    public SingleResponse<PayOrderCreateDTO> refund(@RequestBody @Validated PayOrderCreateCommand command) {
        return SingleResponse.ok(payCommandHandler.refund(command));
    }

    @Operation(summary = "查询支付订单表分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<PayOrderCreateDTO>> queryPages(@RequestBody @Validated PayOrderPageQuery query) {
        return SingleResponse.ok(payQueryService.queryPages(query));
    }

}
