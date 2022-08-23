package com.kt.cloud.order.module.cartitem.controller;

import com.kt.cloud.order.module.cartitem.dto.request.CartAddReqDTO;
import com.kt.cloud.order.module.cartitem.dto.response.CartItemRespDTO;
import com.kt.cloud.order.module.cartitem.service.CartService;
import com.kt.component.dto.ServerResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.validator.ValidateGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import com.kt.component.web.base.BaseController;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Api(tags = "购物车表")
@Validated
@RestController
@RequestMapping("/v1/cart")
public class CartController extends BaseController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ApiOperation(value = "添加商品到购物车")
    @PostMapping("/item/add")
    public SingleResponse<Long> create(@RequestBody @Validated CartAddReqDTO reqDTO) {
        return SingleResponse.ok(cartService.addToCart(reqDTO));
    }

    @ApiOperation(value = "选中购物车项")
    @PostMapping("/item/checked")
    public ServerResponse checkedItem(@RequestBody @Validated(ValidateGroup.Update.class) CartAddReqDTO reqDTO) {
        cartService.updateCartItem(reqDTO);
        return ServerResponse.ok();
    }

    @ApiOperation(value = "查询购物车表详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<CartItemRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(cartService.getCartItemInfo(id));
    }


}
