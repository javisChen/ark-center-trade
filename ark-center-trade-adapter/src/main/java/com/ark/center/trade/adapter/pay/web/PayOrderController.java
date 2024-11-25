package com.ark.center.trade.adapter.pay.web;

import com.alibaba.fastjson2.JSONObject;
import com.ark.center.trade.application.pay.PayOrderCommandHandler;
import com.ark.center.trade.application.pay.PayQueryService;
import com.ark.center.trade.client.pay.PayApi;
import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import com.ark.center.trade.client.pay.query.PayOrderPageQuery;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付订单表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Schema(name = "支付管理", description = "支付管理")
@Validated
@RestController
@RequestMapping("/v1/pay-orders")
@RequiredArgsConstructor
public class PayOrderController extends BaseController implements PayApi {

    private final PayOrderCommandHandler payOrderCommandHandler;
    private final PayQueryService payQueryService;

//    @Operation(summary = "创建支付单")
//    @PostMapping("/create")
//    public SingleResponse<PayOrderCreateDTO> createPayOrder(@RequestBody @Validated PayOrderCreateCommand command) {
//        return SingleResponse.ok(payOrderCommandHandler.createPayOrder(command));
//    }

    @Operation(summary = "获取支付单状态")
    @GetMapping("/status")
    public SingleResponse<Integer> queryPayOrderStatus(@RequestParam Long id) {
        return SingleResponse.ok(payQueryService.getPayOrderStatus(id));
    }

    @Operation(summary = "订单通知")
    @PostMapping("/notify")
    public SingleResponse<Map<String, Object>> handleNotify(@RequestBody JSONObject reqDTO) {
        Map<String, Object> notify = payOrderCommandHandler.handleNotify(reqDTO);
        return SingleResponse.ok(notify);
    }

    @Operation(summary = "查询支付订单表分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<PayOrderCreateDTO>> queryPages(@RequestBody @Validated PayOrderPageQuery query) {
        return SingleResponse.ok(payQueryService.queryPages(query));
    }

    @Operation(summary = "查询支付订单表详情",
            parameters = {
                    @Parameter(name = "id", description = "支付单id")
            }
    )
    @GetMapping("/info")
    public SingleResponse<PayOrderCreateDTO> info(@RequestParam(required = false)
                                                      @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(payQueryService.getPayOrderInfo(id));
    }

}
