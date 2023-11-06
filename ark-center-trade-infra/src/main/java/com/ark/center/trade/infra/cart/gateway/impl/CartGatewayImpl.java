package com.ark.center.trade.infra.cart.gateway.impl;

import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.center.trade.domain.cart.gateway.CartGateway;
import com.ark.center.trade.infra.cart.convertor.CartItemConvertor;
import com.ark.center.trade.domain.cart.CartItemDO;
import com.ark.center.trade.infra.cart.gateway.db.CartItemMapper;
import com.ark.component.context.core.ServiceContext;
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
    public CartItemDO getCartItem(Long userId, Long skuId) {
        LambdaQueryWrapper<CartItemDO> qw = new LambdaQueryWrapper<>();
        qw.eq(CartItemDO::getBuyerId, userId)
                .eq(CartItemDO::getSkuId, skuId)
                .last("limit 1");
        return cartItemMapper.selectOne(qw);
    }

    @Override
    public CartItemDO getCartItem(Long cartItemId) {
        LambdaQueryWrapper<CartItemDO> qw = new LambdaQueryWrapper<>();
        qw.eq(CartItemDO::getId, cartItemId)
                .last("limit 1");
        CartItemDO cartItemDO = cartItemMapper.selectOne(qw);
        return cartItemDO;
    }

    @Override
    public void saveCartItem(CartItemDO cartItem) {
        CartItemDO entity = cartItemConvertor.toCartItemDO(cartItem);
        if (entity.getId() == null) {
            cartItem.setBuyerId(ServiceContext.getCurrentUser().getUserId());
            cartItemMapper.insert(entity);
        } else {
            cartItemMapper.updateCartItemQuantity(cartItem.getId(), cartItem.getQuantity());
        }
        // todo 写缓存
    }

    @Override
    public void updateChecked(CartItemDO cartItem) {
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
