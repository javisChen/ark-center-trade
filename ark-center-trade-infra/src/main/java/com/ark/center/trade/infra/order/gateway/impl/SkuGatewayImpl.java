package com.ark.center.trade.infra.order.gateway.impl;

import com.ark.center.commodity.api.sku.request.SkuInfoGetReqDTO;
import com.ark.center.commodity.api.sku.response.SkuRespDTO;
import com.ark.center.trade.domain.order.gateway.SkuGateway;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.infra.order.convertor.SkuConvertor;
import com.ark.center.trade.infra.order.gateway.impl.rpc.SkuServiceApi;
import com.ark.component.microservice.rpc.util.RpcUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkuGatewayImpl implements SkuGateway {

    private final SkuServiceApi skuServiceApi;

//    public List<SkuRespDTO> getSkuInfoList(List<Long> skuIds) {
//        SkuInfoGetReqDTO skuInfoGetReqDTO = new SkuInfoGetReqDTO();
//        skuInfoGetReqDTO.setSkuIds(skuIds);
//        return RpcUtils.checkAndGetData(skuServiceApi.getSkuInfoList(skuInfoGetReqDTO));
//    }
//
//    public SkuRespDTO getSkuInfoList(Long skuId) {
//        List<SkuRespDTO> skuInfoList = getSkuInfoList(Lists.newArrayList(skuId));
//        if (CollUtil.isNotEmpty(skuInfoList)) {
//            return skuInfoList.get(0);
//        }
//        throw ExceptionFactory.userException("SKU已失效或已下架");
//    }

    @Override
    public List<Sku> getSkuList(List<Long> skuIds) {
        SkuInfoGetReqDTO skuInfoGetReqDTO = new SkuInfoGetReqDTO();
        skuInfoGetReqDTO.setSkuIds(skuIds);
        List<SkuRespDTO> skuRespDTOList = RpcUtils.checkAndGetData(skuServiceApi.getSkuInfoList(skuInfoGetReqDTO));
        return SkuConvertor.toDomainObjects(skuRespDTOList);
    }

}
