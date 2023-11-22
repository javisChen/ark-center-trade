package com.ark.center.trade.infra.cart.gateway.impl;

import com.ark.center.trade.client.cartitem.dto.CartItemDTO;
import com.ark.center.trade.domain.cart.CartItem;
import com.ark.center.trade.domain.cart.gateway.CartGateway;
import com.ark.center.trade.infra.cart.convertor.CartItemConvertor;
import com.ark.center.trade.infra.cart.gateway.db.CartItemMapper;
import com.ark.component.context.core.ServiceContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartGatewayImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartGateway {

    private final CartItemMapper cartItemMapper;

    private final CartItemConvertor cartItemConvertor;

    @Override
    public CartItem selectItem(Long userId, Long skuId) {
        LambdaQueryWrapper<CartItem> qw = new LambdaQueryWrapper<>();
        qw.eq(CartItem::getBuyerId, userId)
                .eq(CartItem::getSkuId, skuId)
                .last("limit 1");
        return cartItemMapper.selectOne(qw);
    }

    @Override
    public CartItem selectById(Long cartItemId) {
        LambdaQueryWrapper<CartItem> qw = new LambdaQueryWrapper<>();
        qw.eq(CartItem::getId, cartItemId)
                .last("limit 1");
        return cartItemMapper.selectOne(qw);
    }

    @Override
    public void insert(CartItem cartItem) {
        if (cartItem.getId() == null) {
            cartItem.setBuyerId(ServiceContext.getCurrentUser().getUserId());
            cartItemMapper.insert(cartItem);
        } else {
            cartItemMapper.updateCartItemQuantity(cartItem.getId(), cartItem.getQuantity());
        }
        // todo 写缓存
    }

    @Override
    public void updateChecked(CartItem cartItem, Boolean checked) {
        CartItem entity = new CartItem();
        entity.setChecked(checked);
        entity.setId(cartItem.getId());
        cartItemMapper.updateById(entity);
    }

    @Override
    public List<CartItemDTO> selectByBuyer(Long userId) {
        LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItem::getBuyerId, userId);
        List<CartItem> cartItems = cartItemMapper.selectList(queryWrapper);
        return cartItemConvertor.toCartItemDTO(cartItems);
    }

    @Override
    public void updateQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setQuantity(quantity);
        updateById(cartItem);
    }
}
