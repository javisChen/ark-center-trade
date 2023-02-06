package com.ark.center.trade.infra.order.convertor;

import com.google.common.collect.Lists;

import com.ark.center.commodity.api.sku.response.SkuRespDTO;
import com.ark.center.trade.domain.order.model.Sku;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkuConvertor {

    public static List<Sku> toDomainObjects(List<SkuRespDTO> respDTOList) {
        List<Sku> skuList = Lists.newArrayListWithCapacity(respDTOList.size());
        for (SkuRespDTO skuRespDTO : respDTOList) {
            Sku sku = new Sku();
            sku.setSkuId(skuRespDTO.getId());
            sku.setSpuName(skuRespDTO.getSpuName());
            sku.setSalesPrice(skuRespDTO.getSalesPrice());
            sku.setCostPrice(skuRespDTO.getCostPrice());
            sku.setStock(skuRespDTO.getStock());
            sku.setWarnStock(skuRespDTO.getWarnStock());
            sku.setMainPicture(skuRespDTO.getMainPicture());
            sku.setSpecList(skuRespDTO.getSpecList());
            skuList.add(sku);
        }
        return skuList;
    }

}
