package com.ark.center.trade.client.cartitem.command;

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
@Schema(name = "CartAddReqDTO", description = "添加商品进购物车模型")
public class CartItemCmd implements Serializable {

    @Schema(name = "SKU ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "SKU ID不能为空")
    private Long skuId;

}