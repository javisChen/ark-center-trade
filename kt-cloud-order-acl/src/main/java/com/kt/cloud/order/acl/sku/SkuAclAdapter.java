package com.kt.cloud.order.acl.sku;

import com.kt.cloud.commodity.api.sku.request.SkuInfoGetReqDTO;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.component.microservice.rpc.util.RpcUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkuAclAdapter {

    private final SkuAclApi skuAclApi;

    public SkuAclAdapter(SkuAclApi skuAclApi) {
        this.skuAclApi = skuAclApi;
    }

    public List<SkuRespDTO> getSkuInfoList(SkuInfoGetReqDTO skuInfoGetReqDTO) {
        return RpcUtils.checkAndGetData(skuAclApi.getSkuInfoList(skuInfoGetReqDTO));
    }
}
