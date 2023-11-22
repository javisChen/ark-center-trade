package com.ark.center.trade.client.cartitem.command;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 添加商品进购物车模型
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Data
@ApiModel(value = "CartItemCheckReqDTO", description = "添加商品进购物车模型")
public class CartItemCheckCmd implements Serializable {

    @Schema(description = "购物车项", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "选项ID不能为空")
    private Long cartItemId;

    @Schema(description = "是否选中", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否选中不能为空")
    private Boolean checked;

}