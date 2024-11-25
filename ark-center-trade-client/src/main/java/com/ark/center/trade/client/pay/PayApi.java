package com.ark.center.trade.client.pay;

import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "${ark.center.pay.service.name:pay}",
        path = "/v1/pay",
        url = "${ark.center.pay.service.uri:}",
        dismiss404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface PayApi {
//
//    @Operation(summary = "创建支付单")
//    @PostMapping("/orders")
//    SingleResponse<PayOrderCreateDTO> createPayOrder(@RequestBody Validated PayOrderCreateCommand);
}
