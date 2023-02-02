package com.ark.center.trade.api;

import com.ark.center.trade.api.response.OrderDetailRespDTO;
import com.ark.component.dto.SingleResponse;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "${ark.center.trade.service.name:order}",
    path = "/v1/order",
    url = "${ark.center.trade.service.uri:}",
    decode404 = true,
    configuration = {FeignCommonErrorDecoder.class}
)
public interface OrderApi {

    @GetMapping({"/info"})
    SingleResponse<OrderDetailRespDTO> getOrderById(@RequestParam(value = "id") Long id);

}
