package com.ark.center.trade.infra.order.gateway.impl.rpc;

import com.ark.center.product.client.goods.rpc.SkuApi;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "${ark.center.product.service.name:product}",
        path = "/v1/skus",
        url = "${ark.center.product.service.uri:}",
        dismiss404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface SkuRemoteApi extends SkuApi {

}
