package com.ark.center.trade.infra.order.gateway.impl.rpc;

import com.ark.center.product.client.stock.StockApi;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "${ark.center.commodity.service.name:commodity}",
        path = "/v1/inner/stock",
        url = "${ark.center.commodity.service.uri:}",
        dismiss404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface StockRemoteApi extends StockApi {

}
