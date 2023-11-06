package com.ark.center.trade.application.cart;

import com.alibaba.fastjson.JSON;
import com.ark.center.trade.client.cartitem.command.CartItemAddCmd;
import com.ark.center.trade.client.client.command.CartItemCheckCmd;
import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.center.trade.domain.cart.CartItemDO;
import com.ark.center.trade.domain.cart.gateway.CartGateway;
import com.ark.center.trade.domain.order.gateway.SkuGateway;
import com.ark.center.trade.domain.order.model.Sku;
import com.ark.center.trade.infra.cart.convertor.CartItemConvertor;
import com.ark.component.context.core.ServiceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartAppService {

    private final CartGateway cartGateway;
    private final CartItemConvertor cartItemConvertor;
    private final SkuGateway skuGateway;

    @Transactional(rollbackFor = Throwable.class)
    public void save(CartItemAddCmd cmd) {
        Long currentUserId = ServiceContext.getCurrentUser().getUserId();
        Long skuId = cmd.getSkuId();

        CartItemDO cartItem = cartGateway.getCartItem(currentUserId, skuId);

        if (cartItem == null) {
            List<Sku> skuList = skuGateway.getSkuList(Collections.singletonList(skuId));
            Sku sku = skuList.get(0);
            cartItem = new CartItemDO();
            cartItem.setBuyerId(currentUserId);
            cartItem.setSkuId(skuId);
            cartItem.setSpuName(sku.getSpuName());
            cartItem.setPrice(sku.getSalesPrice());
            cartItem.setQuantity(1);
            cartItem.setExpectAmount(sku.getSalesPrice());
            cartItem.setActualAmount(sku.getSalesPrice());
            cartItem.setPicUrl(sku.getMainPicture());
            cartItem.setSpecData(JSON.toJSONString(sku.getSpecList()));
            cartItem.setChecked(true);
            cartGateway.insert(cartItem);
        } else {
            cartGateway.updateCartItemQuantity(cartItem.getId(), cartItem.getQuantity() + 1);
        }
    }

    public void checkCartItem(CartItemCheckCmd cmd) {
        CartItemDO cartItem = cartGateway.getCartItem(cmd.getCartItemId());
        cartItem.checked(cmd.getChecked());
        cartGateway.updateChecked(cartItem);
    }

    public List<CartItemDTO> listBuyerCartItems() {
        Long currentUserId = ServiceContext.getCurrentUser().getUserId();
        return cartGateway.listCartItems(currentUserId);
    }
}
