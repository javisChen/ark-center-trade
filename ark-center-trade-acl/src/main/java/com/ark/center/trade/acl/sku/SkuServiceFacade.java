//package com.ark.center.trade.acl.sku;
//import cn.hutool.core.collection.CollUtil;
//import com.google.common.collect.Lists;
//
//import com.ark.center.commodity.api.sku.request.SkuInfoGetReqDTO;
//import com.ark.center.commodity.api.sku.response.SkuRespDTO;
//import com.ark.component.exception.ExceptionFactory;
//import com.ark.component.microservice.rpc.util.RpcUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class SkuServiceFacade {
//
//    private final SkuServiceApi skuServiceApi;
//
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
//}
