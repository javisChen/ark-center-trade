package com.kt.cloud.order.api;

import com.kt.cloud.order.api.response.OrderDetailRespDTO;
import com.kt.component.dto.SingleResponse;
import com.kt.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "${kt.cloud.order.service.name:order}",
    path = "/v1/order",
    url = "${kt.cloud.order.service.uri:}",
    decode404 = true,
    configuration = {FeignCommonErrorDecoder.class}
)
public interface OrderApi {

    @GetMapping({"/info"})
    SingleResponse<OrderDetailRespDTO> getOrderById(@RequestParam(value = "id") Long id);

}
