package com.kt.cloud.order.module.receive.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
@ApiModel(value = "ReceiveUpdateReqDTO对象", description = "订单收货信息")
public class ReceiveUpdateReqDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "订单ID", required = true)
    @NotNull(message = "订单ID不能为空")
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

}