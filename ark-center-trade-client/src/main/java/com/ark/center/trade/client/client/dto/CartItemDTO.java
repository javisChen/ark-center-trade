package com.ark.center.trade.client.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "买家ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long buyerId;

    @Schema(description = "SKU ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long skuId;

    @Schema(description = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String productName;

    @Schema(description = "SKU单价", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer price;

    @Schema(description = "购买数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @Schema(description = "应付金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer expectAmount;

    @Schema(description = "实付金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer actualAmount;

    @Schema(description = "图片地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String picUrl;

    @Schema(description = "SKU销售参数JSON", requiredMode = Schema.RequiredMode.REQUIRED)
    private String specData;

    @Schema(description = "是否选中 enums[YES,是,1;NO,否,0]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean checked;

}
