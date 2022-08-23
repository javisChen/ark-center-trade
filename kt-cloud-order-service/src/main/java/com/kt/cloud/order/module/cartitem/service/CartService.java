package com.kt.cloud.order.module.cartitem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.cloud.order.acl.sku.SkuServiceFacade;
import com.kt.cloud.order.dao.entity.CartItemDO;
import com.kt.cloud.order.dao.mapper.CartItemMapper;
import com.kt.cloud.order.module.cartitem.CartConvertor;
import com.kt.cloud.order.module.cartitem.dto.request.CartAddReqDTO;
import com.kt.cloud.order.module.cartitem.dto.request.CartItemPageQueryReqDTO;
import com.kt.cloud.order.module.cartitem.dto.response.CartItemRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.context.core.ServiceContext;
import com.kt.component.dto.PageResponse;
import com.kt.component.exception.BizException;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;
import com.kt.component.orm.mybatis.base.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Service
public class CartService extends ServiceImpl<CartItemMapper, CartItemDO> implements IService<CartItemDO> {

    private final SkuServiceFacade skuServiceFacade;

    public CartService(SkuServiceFacade skuServiceFacade) {
        this.skuServiceFacade = skuServiceFacade;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Long addToCart(CartAddReqDTO reqDTO) {
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
            throw new BizException("添加购物车失败，商品数量已到达上限");
        }
    }

    private CartItemDO findCartItem(Long currentUserId, Long skuId) {
        return lambdaQuery()
                .eq(CartItemDO::getBuyerId, currentUserId)
                .eq(CartItemDO::getSkuId, skuId)
                .one();
    }

    public PageResponse<CartItemRespDTO> getPageList(CartItemPageQueryReqDTO queryDTO) {
        IPage<CartItemRespDTO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, CartItemRespDTO.class));
        return BeanConvertor.copyPage(page, CartItemRespDTO.class);
    }

    public Long updateCartItem(CartAddReqDTO reqDTO) {
        CartItemDO entity = BeanConvertor.copy(reqDTO, CartItemDO.class);
        updateById(entity);
        return entity.getId();
    }

    public CartItemRespDTO getCartItemInfo(Long CartItemId) {
        CartItemDO entity = getById(CartItemId);
        return BeanConvertor.copy(entity, CartItemRespDTO.class);
    }

}
