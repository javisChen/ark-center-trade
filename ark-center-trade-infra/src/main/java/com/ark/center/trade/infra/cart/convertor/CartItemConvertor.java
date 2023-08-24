package com.ark.center.trade.infra.cart.convertor;

import com.ark.center.trade.client.cartitem.command.CartItemAddCmd;
import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.center.trade.domain.cart.model.CartItem;
import com.ark.center.trade.infra.cart.gateway.db.CartItemDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface CartItemConvertor {

    List<CartItem> toCartItemDomainObject(List<CartItemDO> cartItemDOList);

    List<CartItemDTO> toCartItemDTO(List<CartItemDO> cartItemDOList);

    @Mappings({
            @Mapping(target = "checked", expression = "java(cartItemDO.getChecked() ? 1: 0)")
    })
    CartItemDTO toCartItemDTO(CartItemDO cartItemDO);

    CartItem toCartItemDomainObject(CartItemDO cartItemDO);

    CartItem toCartItemDomainObject(CartItemAddCmd cartItemAddCmd);

    CartItemDO toCartItemDO(CartItem cartItem);
}
