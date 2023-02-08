package com.ark.center.trade.domain.cart.gateway;

import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.dto.OrderItemDTO;
import com.ark.center.trade.client.order.query.OrderPageQry;
import com.ark.center.trade.domain.cart.model.CartItem;
import com.ark.center.trade.domain.order.model.Order;
import com.ark.component.dto.PageResponse;

import java.util.List;

public interface CartGateway {

    CartItem getCartItem(Long userId, Long skuId);

    CartItem getCartItem(Long cartItemId);

    void saveCartItem(CartItem cartItem);

    void updateChecked(CartItem cartItem);

    List<CartItemDTO> listCartItems(Long userId);
}
