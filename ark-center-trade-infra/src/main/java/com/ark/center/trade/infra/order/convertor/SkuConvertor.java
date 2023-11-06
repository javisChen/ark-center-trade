package com.ark.center.trade.infra.order.convertor;

import com.ark.center.commodity.client.commodity.dto.AttrDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.domain.order.model.SkuAttr;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkuConvertor {

    List<Sku> toSkuDomainObject(List<SkuDTO> skuDTOList);

    List<SkuAttr> toSkuAttrDomainObject(List<AttrDTO> skuAttrDTOList);

}
