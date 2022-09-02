package com.kt.cloud.order.module.cart.controller;

import com.kt.cloud.order.module.cart.dto.request.CartItemAddReqDTO;
import com.kt.cloud.order.module.cart.dto.request.CartItemCheckReqDTO;
import com.kt.cloud.order.module.cart.dto.response.CartItemRespDTO;
import com.kt.cloud.order.module.cart.service.CartItemService;
import com.kt.component.dto.MultiResponse;
import com.kt.component.dto.ServerResponse;
import com.kt.component.dto.SingleResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
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
@Api(tags = "购物车服务")
@Validated
@RestController
@RequestMapping("/v1/cart")
public class CartController extends BaseController {
    private final CartItemService cartItemService;
    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @ApiOperation(value = "添加商品到购物车")
    @PostMapping("/item/add")
    public SingleResponse<Long> create(@RequestBody @Validated CartItemAddReqDTO reqDTO) {
        return SingleResponse.ok(cartItemService.addOrUpdateCartItem(reqDTO));
    }

    @ApiOperation(value = "选中购物车项")
    @PostMapping("/item/checked")
    public ServerResponse checkCartItem(@RequestBody @Validated CartItemCheckReqDTO reqDTO) {
        cartItemService.checkCartItem(reqDTO);
        return ServerResponse.ok();
    }

    @ApiOperation(value = "获取用户的购物车信息")
    @GetMapping("/items")
    public MultiResponse<CartItemRespDTO> listBuyerItems() {
        return MultiResponse.ok(cartItemService.listBuyerCartItems());
    }

}
