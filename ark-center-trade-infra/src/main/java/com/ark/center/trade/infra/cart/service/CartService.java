package com.ark.center.trade.infra.cart.service;

import com.ark.center.trade.client.cartitem.dto.CartItemDTO;
import com.ark.center.trade.infra.cart.CartItem;
import com.ark.center.trade.infra.cart.convertor.CartItemConvertor;
import com.ark.center.trade.infra.cart.db.CartItemMapper;
import com.ark.component.context.core.ServiceContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartService extends ServiceImpl<CartItemMapper, CartItem>  {

    private final CartItemMapper cartItemMapper;

    private final CartItemConvertor cartItemConvertor;

    public CartItem selectItem(Long userId, Long skuId) {
        LambdaQueryWrapper<CartItem> qw = new LambdaQueryWrapper<>();
        qw.eq(CartItem::getBuyerId, userId)
                .eq(CartItem::getSkuId, skuId)
                .last("limit 1");
        return cartItemMapper.selectOne(qw);
    }

    public CartItem selectById(Long cartItemId) {
        LambdaQueryWrapper<CartItem> qw = new LambdaQueryWrapper<>();
        qw.eq(CartItem::getId, cartItemId)
                .last("limit 1");
        return cartItemMapper.selectOne(qw);
    }

    public void insert(CartItem cartItem) {
        if (cartItem.getId() == null) {
            cartItem.setBuyerId(ServiceContext.getCurrentUser().getUserId());
            cartItemMapper.insert(cartItem);
        } else {
            cartItemMapper.updateCartItemQuantity(cartItem.getId(), cartItem.getQuantity());
        }
        // todo 写缓存
    }

    public void updateChecked(CartItem cartItem, Boolean checked) {
        CartItem entity = new CartItem();
        entity.setChecked(checked);
        entity.setId(cartItem.getId());
        cartItemMapper.updateById(entity);
    }

    public List<CartItemDTO> selectByBuyer(Long userId) {
        LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItem::getBuyerId, userId);
        List<CartItem> cartItems = cartItemMapper.selectList(queryWrapper);
        return cartItemConvertor.toCartItemDTO(cartItems);
    }

    public void updateQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setQuantity(quantity);
        updateById(cartItem);
    }

    public void deleteByIds(List<Long> itemIds) {
        LambdaQueryWrapper<CartItem> wrapper = new LambdaQueryWrapper<CartItem>().in(CartItem::getId, itemIds);
        remove(wrapper);
    }

    public void delete(LambdaUpdateWrapper<CartItem> wrapper) {
        remove(wrapper);
    }


    public void removeBuyerCartItems(Long buyer, List<Long> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            log.warn("skuIds is empty");
            return;
        }
        LambdaUpdateWrapper<CartItem> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(CartItem::getSkuId, skuIds);
        wrapper.eq(CartItem::getBuyerId, buyer);
        delete(wrapper);
    }
}
