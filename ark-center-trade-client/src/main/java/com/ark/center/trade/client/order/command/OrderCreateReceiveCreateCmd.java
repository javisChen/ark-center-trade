package com.ark.center.trade.client.order.command;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 订单收货信息
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderCreateReceiveCreateCmd", description = "订单收货信息")
public class OrderCreateReceiveCreateCmd implements Serializable {

    @Schema(description = "收货人名称", required = true)
    private String name;

    @Schema(description = "收货人电话", required = true)
    private String mobile;

    @Schema(description = "省份", required = true)
    private String province;

    @Schema(description = "城市", required = true)
    private String city;

    @Schema(description = "区", required = true)
    private String district;

    @Schema(description = "街道", required = false)
    private String street;

    @Schema(description = "详细地址", required = true)
    private String address;

}
