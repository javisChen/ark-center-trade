package com.ark.center.trade.client.client.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
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

    @Schema(description = "购物车项", required = true)
    @NotNull(message = "选项ID不能为空")
    private Long cartItemId;

    @Schema(description = "是否选中", required = true)
    @NotNull(message = "是否选中不能为空")
    private Boolean checked;

}