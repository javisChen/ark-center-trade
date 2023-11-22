package com.ark.center.trade.domain.cart.gateway;

import com.ark.center.trade.client.cartitem.dto.CartItemDTO;
import com.ark.center.trade.domain.cart.CartItem;

import java.util.List;

public interface CartGateway {

    /**
     * 根据用户和商品id查询购物车商品
     */
    CartItem selectItem(Long userId, Long skuId);
    CartItem selectById(Long cartItemId);
    void insert(CartItem cartItem);

    void updateChecked(CartItem cartItem, Boolean checked);

    List<CartItemDTO> selectByBuyer(Long userId);

    void updateQuantity(Long cartItemId, Integer quantity);
}
