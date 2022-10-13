package com.ark.center.order.module.cart.service;

import com.ark.center.order.acl.sku.SkuServiceFacade;
import com.ark.center.order.dao.entity.CartItemDO;
import com.ark.center.order.dao.mapper.CartItemMapper;
import com.ark.center.order.module.cart.CartConvertor;
import com.ark.center.order.module.cart.dto.request.CartItemAddReqDTO;
import com.ark.center.order.module.cart.dto.request.CartItemCheckReqDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ark.center.order.module.cart.dto.response.CartItemRespDTO;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.exception.ExceptionFactory;
import org.springframework.stereotype.Service;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.component.orm.mybatis.base.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Service
public class CartItemService extends ServiceImpl<CartItemMapper, CartItemDO> implements IService<CartItemDO> {

    private final SkuServiceFacade skuServiceFacade;

    public CartItemService(SkuServiceFacade skuServiceFacade) {
        this.skuServiceFacade = skuServiceFacade;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Long addOrUpdateCartItem(CartItemAddReqDTO reqDTO) {
        Long currentUserId = ServiceContext.getCurrentUser().getUserId();
        Long skuId = reqDTO.getSkuId();
        CartItemDO cartItemDO = findCartItem(currentUserId, skuId);
        if (cartItemDO == null) {
            cartItemDO = CartConvertor.convertFromSkuResp(skuServiceFacade.getSkuInfoList(skuId), currentUserId);
            // 写DB
            saveCartItemToDB(cartItemDO);
            // 写缓存
            saveCartItemToCache(cartItemDO);
        } else {
            addCartItemQuantity(cartItemDO.getId(), 1);
        }
        return cartItemDO.getId();
    }

    private void saveCartItemToCache(CartItemDO cartItemDO) {
        // todo
    }

    private void saveCartItemToDB(CartItemDO cartItemDO) {
        save(cartItemDO);
    }

    private void addCartItemQuantity(Long cartItemId, int quantity) {
        int result = baseMapper.updateCartItemQuantity(cartItemId, quantity);
        if (result < 1) {
            throw ExceptionFactory.userException("添加购物车失败，商品数量已到达上限");
        }
    }

    private CartItemDO findCartItem(Long currentUserId, Long skuId) {
        return lambdaQuery()
                .eq(CartItemDO::getBuyerId, currentUserId)
                .eq(CartItemDO::getSkuId, skuId)
                .one();
    }

    public void checkCartItem(CartItemCheckReqDTO reqDTO) {
        lambdaUpdate()
                .eq(BaseEntity::getId, reqDTO.getCartItemId())
                .set(CartItemDO::getChecked, reqDTO.getChecked())
                .update();
    }

    public List<CartItemRespDTO> listBuyerCartItems() {
        Long currentUserId = ServiceContext.getCurrentUser().getUserId();
        List<CartItemDO> doList = lambdaQuery()
                .eq(CartItemDO::getBuyerId, currentUserId)
                .list();
        return BeanConvertor.copyList(doList, CartItemRespDTO.class);
    }
}
