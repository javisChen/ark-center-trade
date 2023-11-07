package com.ark.center.trade.infra.cart.convertor;

import com.ark.center.trade.client.cartitem.command.CartItemCmd;
import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.center.trade.domain.cart.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemConvertor {

    List<CartItemDTO> toCartItemDTO(List<CartItem> cartItemList);

    // @Mappings({
    //         @Mapping(target = "checked", expression = "java(cartItemDO.getChecked() ? 1: 0)")
    // })
    // CartItemDTO toCartItemDTO(CartItem cartItem);

    CartItem toCartItemDomainObject(CartItemCmd cartItemCmd);

    CartItem toCartItemDO(CartItemCmd cartItem);
}
