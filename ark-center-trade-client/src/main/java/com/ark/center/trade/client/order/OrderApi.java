package com.ark.center.trade.client.order;

import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.component.dto.SingleResponse;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "${ark.center.trade.service.name:trade}",
    path = "/v1/order",
    url = "${ark.center.trade.service.uri:}",
    dismiss404 = true,
    configuration = {FeignCommonErrorDecoder.class}
)
public interface OrderApi {

    @GetMapping({"/details"})
    SingleResponse<OrderDTO> queryDetails(@RequestParam(value = "id") Long id);

}
