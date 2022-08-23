package com.kt.cloud.order.module.cartitem.dto.request;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CartItemPageQueryReqDTO对象", description = "购物车表")
public class CartItemPageQueryReqDTO extends PagingQuery {


    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @ApiModelProperty(value = "SKU ID")
    private Long skuId;

    @ApiModelProperty(value = "SKU单价")
    private Integer price;

    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

    @ApiModelProperty(value = "应付金额")
    private Integer expectAmount;

    @ApiModelProperty(value = "实付金额")
    private Integer actualAmount;

    @ApiModelProperty(value = "图片地址")
    private String picUrl;

    @ApiModelProperty(value = "SKU销售参数JSON")
    private String specData;

    @ApiModelProperty(value = "是否选中 enums[YES,是,1;NO,否,0]")
    private Integer checked;

}
