package com.kt.cloud.order.acl.sku;

import com.kt.cloud.commodity.api.sku.SkuApi;
import com.kt.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "${kt.cloud.commodity.service.name:commodity}",
        path = "/v1/sku",
        url = "${kt.cloud.commodity.service.uri:}",
        decode404 = true,
        configuration = {FeignCommonErrorDecoder.class}
)
public interface SkuServiceApi extends SkuApi {
}
