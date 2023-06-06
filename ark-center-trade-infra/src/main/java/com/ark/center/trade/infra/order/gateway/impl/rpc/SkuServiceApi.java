package com.ark.center.trade.infra.order.gateway.impl.rpc;

import com.ark.center.commodity.client.commodity.rpc.SkuApi;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "${ark.center.commodity.service.name:commodity}",
        path = "/v1/sku",
        url = "${ark.center.commodity.service.uri:}",
        dismiss404 = true,
        configuration = {FeignCommonErrorDecoder.class}
)
public interface SkuServiceApi extends SkuApi {

}
