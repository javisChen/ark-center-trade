package com.ark.center.trade.application.cart;

import com.alibaba.fastjson.JSON;
import com.ark.center.commodity.api.sku.response.SkuRespDTO;
import com.ark.center.trade.dao.entity.CartItemDO;

public class CartConvertor {

    public static CartItemDO convertFromSkuResp(SkuRespDTO respDTO, Long currentUserId) {
        CartItemDO cartItemDO = new CartItemDO();
        cartItemDO.setBuyerId(currentUserId);
        cartItemDO.setSkuId(respDTO.getId());
        cartItemDO.setSpuName(respDTO.getSpuName());
        cartItemDO.setPrice(respDTO.getSalesPrice());
        cartItemDO.setQuantity(1);
        cartItemDO.setExpectAmount(respDTO.getSalesPrice());
        cartItemDO.setActualAmount(respDTO.getSalesPrice());
        cartItemDO.setPicUrl(respDTO.getMainPicture());
        cartItemDO.setSpecData(JSON.toJSONString(respDTO.getSpecList()));
        cartItemDO.setChecked(CartItemDO.Checked.YES.getValue());
        return cartItemDO;

    }
}
