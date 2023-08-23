package com.ark.center.trade.client.client.command;

import java.io.Serializable;
import jakarta.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 添加商品进购物车模型
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Data
@ApiModel(value = "CartAddReqDTO", description = "添加商品进购物车模型")
public class CartItemAddCmd implements Serializable {

    @Schema(description = "SKU ID", required = true)
    @NotNull(message = "SKU ID不能为空")
    private Long skuId;

}