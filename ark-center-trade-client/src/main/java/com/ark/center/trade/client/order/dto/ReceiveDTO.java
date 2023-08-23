package com.ark.center.trade.client.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单收货信息
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "ReceiveDTO", description = "订单收货信息")
public class ReceiveDTO implements Serializable {

    @Schema(description = "id", required = true)
    private Long id;

    @Schema(description = "订单ID", required = true)
    private Long orderId;

    @Schema(description = "收货人名称", required = false)
    private String name;

    @Schema(description = "收货人电话", required = false)
    private String mobile;

    @Schema(description = "省份", required = false)
    private String province;

    @Schema(description = "城市", required = false)
    private String city;

    @Schema(description = "区", required = false)
    private String district;

    @Schema(description = "详细地址", required = false)
    private String address;

    @Schema(description = "街道", required = false)
    private String street;

}
