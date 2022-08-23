package com.kt.cloud.order.module.cart;

import com.alibaba.fastjson.JSON;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.cloud.order.dao.entity.CartItemDO;

public class CartConvertor {

    public static CartItemDO convertFromSkuResp(SkuRespDTO respDTO, Long currentUserId) {
        CartItemDO cartItemDO = new CartItemDO();
        cartItemDO.setBuyerId(currentUserId);
        cartItemDO.setSkuId(respDTO.getId());
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
