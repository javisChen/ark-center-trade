package com.ark.center.trade.infra.order.gateway.impl;

import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
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
    private final SkuConvertor skuConvertor;

    @Override
    public List<Sku> getSkuList(List<Long> skuIds) {
        SkuQry qry = new SkuQry();
        qry.setSkuIds(skuIds);
        List<SkuDTO> skuRespDTOList = RpcUtils.checkAndGetData(skuServiceApi.listSku(qry));
        return skuConvertor.toSkuDomainObject(skuRespDTOList);
    }

}
