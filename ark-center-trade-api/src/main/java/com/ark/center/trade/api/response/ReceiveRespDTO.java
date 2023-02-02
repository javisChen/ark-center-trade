package com.ark.center.trade.api.response;

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
@ApiModel(value = "ReceiveRespDTO对象", description = "订单收货信息")
public class ReceiveRespDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "订单ID", required = true)
    private Long orderId;

    @ApiModelProperty(value = "收货人名称", required = false)
    private String name;

    @ApiModelProperty(value = "收货人电话", required = false)
    private String mobile;

    @ApiModelProperty(value = "省份", required = false)
    private String province;

    @ApiModelProperty(value = "城市", required = false)
    private String city;

    @ApiModelProperty(value = "区", required = false)
    private String district;

    @ApiModelProperty(value = "详细地址", required = false)
    private String address;

    @ApiModelProperty(value = "街道", required = false)
    private String street;

}
