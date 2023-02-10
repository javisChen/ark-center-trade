package com.ark.center.trade.infra.order.convertor;

import com.ark.center.commodity.client.commodity.dto.SkuAttrDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.domain.order.model.SkuAttr;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkuConvertor {

    List<Sku> toSkuDomainObject(List<SkuDTO> skuDTOList);

    List<SkuAttr> toSkuAttrDomainObject(List<SkuAttrDTO> skuAttrDTOList);
//    List<Sku> toSkuDomainObject(List<SkuDTO> skuDTOList) {
//        List<Sku> skuList = Lists.newArrayListWithCapacity(skuDTOList.size());
//        for (SkuDTO skuDTO : skuDTOList) {
//            Sku sku = new Sku();
//            sku.setSkuId(skuDTO.getId());
//            sku.setSpuName(skuDTO.getSpuName());
//            sku.setSalesPrice(skuDTO.getSalesPrice());
//            sku.setCostPrice(skuDTO.getCostPrice());
//            sku.setStock(skuDTO.getStock());
//            sku.setWarnStock(skuDTO.getWarnStock());
//            sku.setMainPicture(skuDTO.getMainPicture());
//            sku.setSpecList(skuDTO.getSpecList());
//            skuList.add(sku);
//        }
//        return skuList;
//    }

}
