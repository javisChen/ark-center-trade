package com.kt.cloud.order.module.receive.dto.request;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 订单收货信息
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ReceivePageQueryReqDTO对象", description = "订单收货信息")
public class ReceivePageQueryReqDTO extends PagingQuery {


    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "收货人名称")
    private String name;

    @ApiModelProperty(value = "收货人电话")
    private String mobile;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "详细地址")
    private String address;

}
