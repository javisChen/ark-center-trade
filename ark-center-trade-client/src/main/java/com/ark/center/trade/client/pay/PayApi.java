package com.ark.center.trade.client.pay;

import com.ark.center.trade.client.pay.command.PayOrderCreateCommand;
import com.ark.component.dto.SingleResponse;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${ark.center.pay.service.name:pay}",
        path = "/v1/pay",
        url = "${ark.center.pay.service.uri:}",
        dismiss404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface PayApi {

    @Operation(summary = "创建支付单")
    @PostMapping("/order/create")
    SingleResponse<com.ark.center.trade.client.pay.dto.PayOrderCreateDTO> createPayOrder(@RequestBody
                                                                                         @Validated PayOrderCreateCommand command);
}
