package com.ark.center.trade.infra.order.assembler;


import com.ark.center.product.client.attr.dto.AttrDTO;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.domain.order.model.SkuAttr;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuConvertor {

    List<Sku> toSku(List<SkuDTO> skuDTOList);

    List<SkuAttr> toSkuAttr(List<AttrDTO> skuAttrDTOList);

}
