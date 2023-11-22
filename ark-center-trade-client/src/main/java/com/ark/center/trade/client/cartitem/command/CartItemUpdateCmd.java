package com.ark.center.trade.client.cartitem.command;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "CartItemUpdateCmd", description = "购物车商品更新")
public class CartItemUpdateCmd implements Serializable {

    @Schema(description = "购物车项", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "选项ID不能为空")
    private Long cartItemId;

    @Schema(description = "商品购买数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品购买数量不能为空")
    private Integer quantity;

}