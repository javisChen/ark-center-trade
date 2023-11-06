package com.ark.center.trade.domain.cart.gateway;

import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.center.trade.domain.cart.CartItemDO;

import java.util.List;

public interface CartGateway {

    CartItemDO getCartItem(Long userId, Long skuId);

    CartItemDO getCartItem(Long cartItemId);

    void saveCartItem(CartItemDO cartItem);

    void updateChecked(CartItemDO cartItem);

    List<CartItemDTO> listCartItems(Long userId);
}
