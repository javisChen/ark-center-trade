package com.ark.center.trade.domain.cart.service;

import com.ark.center.trade.domain.cart.CartItem;
import com.ark.center.trade.domain.cart.gateway.CartGateway;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService {

    private final CartGateway cartGateway;

    public void removeBuyerCartItems(Long buyer, List<Long> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            log.warn("skuIds is empty");
            return;
        }
        LambdaUpdateWrapper<CartItem> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(CartItem::getSkuId, skuIds);
        wrapper.eq(CartItem::getBuyerId, buyer);
        cartGateway.delete(wrapper);
    }
}
