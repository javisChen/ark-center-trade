package com.ark.center.order.module.cart.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Data
@ApiModel(value = "CartItemCreateReqDTO对象", description = "购物车表")
public class CartItemCreateReqDTO implements Serializable {



    @ApiModelProperty(value = "买家ID", required = true)
    @NotNull(message = "买家ID不能为空")
    private Long buyerId;

    @ApiModelProperty(value = "SKU ID", required = true)
    @NotNull(message = "SKU ID不能为空")
    private Long skuId;

    @ApiModelProperty(value = "SKU单价", required = true)
    @NotNull(message = "SKU单价不能为空")
    private Integer price;

    @ApiModelProperty(value = "购买数量", required = true)
    @NotNull(message = "购买数量不能为空")
    private Integer quantity;

    @ApiModelProperty(value = "应付金额", required = true)
    @NotNull(message = "应付金额不能为空")
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额", required = true)
    @NotNull(message = "实付金额不能为空")
    private Integer actualAmount;

    @ApiModelProperty(value = "图片地址", required = true)
    @NotBlank(message = "图片地址不能为空")
    private String picUrl;

    @ApiModelProperty(value = "SKU销售参数JSON", required = true)
    @NotBlank(message = "SKU销售参数JSON不能为空")
    private String specData;

    @ApiModelProperty(value = "是否选中 enums[YES,是,1;NO,否,0]", required = true)
    @NotNull(message = "是否选中 enums[YES,是,1;NO,否,0]不能为空")
    private Integer checked;

}
