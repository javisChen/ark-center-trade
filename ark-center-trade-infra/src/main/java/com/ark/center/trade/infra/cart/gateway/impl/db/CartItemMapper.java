package com.ark.center.trade.infra.cart.gateway.impl.db;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
public interface CartItemMapper extends BaseMapper<CartItemDO> {

    int updateCartItemQuantity(@Param("cartItemId") Long cartItemId,
                               @Param("quantity") int quantity);
}
