package com.ark.center.trade.infra.cart.gateway.impl;

import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.center.trade.domain.cart.gateway.CartGateway;
import com.ark.center.trade.domain.cart.model.CartItem;
import com.ark.center.trade.infra.cart.convertor.CartItemConvertor;
import com.ark.center.trade.infra.cart.gateway.db.CartItemDO;
import com.ark.center.trade.infra.cart.gateway.db.CartItemMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartGatewayImpl implements CartGateway {

    private final CartItemMapper cartItemMapper;

    private final CartItemConvertor cartItemConvertor;

    @Override
    public CartItem getCartItem(Long userId, Long skuId) {
        LambdaQueryWrapper<CartItemDO> qw = new LambdaQueryWrapper<>();
        qw.eq(CartItemDO::getBuyerId, userId)
                .eq(CartItemDO::getSkuId, skuId)
                .last("limit 1");
        CartItemDO cartItemDO = cartItemMapper.selectOne(qw);
        return cartItemConvertor.toCartItemDomainObject(cartItemDO);
    }

    @Override
    public CartItem getCartItem(Long cartItemId) {
        LambdaQueryWrapper<CartItemDO> qw = new LambdaQueryWrapper<>();
        qw.eq(CartItemDO::getId, cartItemId)
                .last("limit 1");
        CartItemDO cartItemDO = cartItemMapper.selectOne(qw);
        return cartItemConvertor.toCartItemDomainObject(cartItemDO);
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        CartItemDO entity = cartItemConvertor.toCartItemDO(cartItem);
        if (entity.getId() != null) {
            cartItemMapper.insert(entity);
        } else {
            cartItemMapper.updateCartItemQuantity(cartItem.getId(), cartItem.getQuantity());
        }
        // todo 写缓存
    }

    @Override
    public void updateChecked(CartItem cartItem) {
        CartItemDO entity = new CartItemDO();
        entity.setChecked(cartItem.getChecked());
        entity.setId(cartItem.getId());
        cartItemMapper.updateById(entity);
    }

    @Override
    public List<CartItemDTO> listCartItems(Long userId) {
        LambdaQueryWrapper<CartItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItemDO::getBuyerId, userId);
        List<CartItemDO> cartItemDOS = cartItemMapper.selectList(queryWrapper);
        return cartItemConvertor.toCartItemDTO(cartItemDOS);
    }
}
