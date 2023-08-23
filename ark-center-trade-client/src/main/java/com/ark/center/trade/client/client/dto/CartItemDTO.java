package com.ark.center.trade.client.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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
public class CartItemDTO implements Serializable {

    @Schema(description = "id", required = true)
    private Long id;

    @Schema(description = "买家ID", required = true)
    private Long buyerId;

    @Schema(description = "SKU ID", required = true)
    private Long skuId;

    @Schema(description = "商品名称", required = true)
    private String spuName;

    @Schema(description = "SKU单价", required = true)
    private Integer price;

    @Schema(description = "购买数量", required = true)
    private Integer quantity;

    @Schema(description = "应付金额", required = true)
    private Integer expectAmount;

    @Schema(description = "实付金额", required = true)
    private Integer actualAmount;

    @Schema(description = "图片地址", required = true)
    private String picUrl;

    @Schema(description = "SKU销售参数JSON", required = true)
    private String specData;

    @Schema(description = "是否选中 enums[YES,是,1;NO,否,0]", required = true)
    private Integer checked;

}
