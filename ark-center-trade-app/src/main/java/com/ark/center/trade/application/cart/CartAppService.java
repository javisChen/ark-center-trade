package com.ark.center.trade.application.cart;

import com.alibaba.fastjson.JSON;
import com.ark.center.trade.client.cartitem.command.CartItemCheckCmd;
import com.ark.center.trade.client.cartitem.command.CartItemCmd;
import com.ark.center.trade.client.cartitem.command.CartItemDeleteCmd;
import com.ark.center.trade.client.cartitem.command.CartItemUpdateCmd;
import com.ark.center.trade.client.cartitem.dto.CartItemDTO;
import com.ark.center.trade.infra.cart.CartItem;
import com.ark.center.trade.infra.cart.service.CartService;
import com.ark.center.trade.infra.order.gateway.SkuGateway;
import com.ark.center.trade.infra.order.model.Sku;
import com.ark.component.context.core.ServiceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartAppService {

    private final CartService cartService;

    private final SkuGateway skuGateway;

    @Transactional(rollbackFor = Throwable.class)
    public void save(CartItemCmd cmd) {
        Long currentUserId = ServiceContext.getCurrentUser().getUserId();
        Long skuId = cmd.getSkuId();
        CartItem cartItem = cartService.selectItem(currentUserId, skuId);
        if (cartItem == null) {
            Sku sku = skuGateway.querySku(skuId);
            log.info("Add to cart item, sku = {}", sku);
            cartItem = createCartItem(currentUserId, skuId, sku);
            cartService.insert(cartItem);
        } else {
            cartService.updateQuantity(cartItem.getId(), cartItem.getQuantity() + 1);
        }
    }

    private CartItem createCartItem(Long currentUserId, Long skuId, Sku sku) {
        CartItem cartItem = new CartItem();
        cartItem.setBuyerId(currentUserId);
        cartItem.setSkuId(skuId);
        cartItem.setSkuName(sku.getSkuName());
        cartItem.setPrice(sku.getSalesPrice());
        cartItem.setQuantity(1);
        cartItem.setExpectAmount(sku.getSalesPrice());
        cartItem.setActualAmount(sku.getSalesPrice());
        cartItem.setPicUrl(sku.getMainPicture());
        cartItem.setSpecs(JSON.toJSONString(sku.getSpecs()));
        cartItem.setChecked(true);
        return cartItem;
    }

    public void checkCartItem(CartItemCheckCmd cmd) {
        CartItem cartItem = cartService.selectById(cmd.getCartItemId());
        if (cartItem != null) {
            cartService.updateChecked(cartItem, cmd.getChecked());
        }
    }

    public List<CartItemDTO> queryUserItems() {
        Long currentUserId = ServiceContext.getCurrentUser().getUserId();
        return cartService.selectByBuyer(currentUserId);
    }

    public void updateCartItemQuantity(CartItemUpdateCmd cmd) {
        CartItem cartItem = cartService.selectById(cmd.getCartItemId());
        if (cartItem != null) {
            cartService.updateQuantity(cartItem.getId(), cmd.getQuantity());
        }
    }

    public void deleteCartItems(CartItemDeleteCmd cmd) {
        cartService.deleteByIds(cmd.getCartItemIds());
    }

}
