package com.ark.center.trade.application.cart.dto.response;

import java.io.Serializable;
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
@ApiModel(value = "CartItemRespDTO对象", description = "购物车表")
public class CartItemRespDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "买家ID", required = true)
    private Long buyerId;

    @ApiModelProperty(value = "SKU ID", required = true)
    private Long skuId;

    @ApiModelProperty(value = "商品名称", required = true)
    private String spuName;

    @ApiModelProperty(value = "SKU单价", required = true)
    private Integer price;

    @ApiModelProperty(value = "购买数量", required = true)
    private Integer quantity;

    @ApiModelProperty(value = "应付金额", required = true)
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额", required = true)
    private Integer actualAmount;

    @ApiModelProperty(value = "图片地址", required = true)
    private String picUrl;

    @ApiModelProperty(value = "SKU销售参数JSON", required = true)
    private String specData;

    @ApiModelProperty(value = "是否选中 enums[YES,是,1;NO,否,0]", required = true)
    private Integer checked;

}
