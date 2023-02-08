package com.ark.center.trade.adapter.cart.web;

import com.ark.center.trade.application.cart.CartAppService;
import com.ark.center.trade.client.client.command.CartItemAddCmd;
import com.ark.center.trade.client.client.command.CartItemCheckCmd;
import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import com.ark.component.web.base.BaseController;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Api(tags = "购物车接口")
@Validated
@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
public class CartController extends BaseController {
    private final CartAppService cartAppService;
    @ApiOperation(value = "添加/编辑购物车商品")
    @PostMapping("/item/add")
    public ServerResponse create(@RequestBody @Validated CartItemAddCmd cmd) {
        cartAppService.addOrUpdateCartItem(cmd);
        return ServerResponse.ok();
    }

    @ApiOperation(value = "选中购物车项")
    @PostMapping("/item/checked")
    public ServerResponse checkCartItem(@RequestBody @Validated CartItemCheckCmd cmd) {
        cartAppService.checkCartItem(cmd);
        return ServerResponse.ok();
    }

    @ApiOperation(value = "获取用户的购物车信息")
    @GetMapping("/items")
    public MultiResponse<CartItemDTO> listBuyerItems() {
        return MultiResponse.ok(cartAppService.listBuyerCartItems());
    }

}
