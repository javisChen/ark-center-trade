package com.ark.center.order.module.receive.dto.request;

import java.time.LocalDateTime;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
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
@ApiModel(value = "ReceiveCreateReqDTO对象", description = "订单收货信息")
public class ReceiveCreateReqDTO implements Serializable {


    @ApiModelProperty(value = "收货人名称", required = true)
    private String name;

    @ApiModelProperty(value = "收货人电话", required = true)
    private String mobile;

    @ApiModelProperty(value = "省份", required = true)
    private String province;

    @ApiModelProperty(value = "城市", required = true)
    private String city;

    @ApiModelProperty(value = "区", required = true)
    private String district;

    @ApiModelProperty(value = "街道", required = false)
    private String street;

    @ApiModelProperty(value = "详细地址", required = true)
    private String address;

}
