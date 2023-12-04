package com.ark.center.trade.client.receive.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@Schema(name = "ReceiveCmd", description = "保存收货信息")
public class ReceiveCmd implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "收货人名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    @Schema(name = "收货人电话", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mobile;

    @Schema(name = "省份", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String province;

    @Schema(name = "城市", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String city;

    @Schema(name = "区", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String district;

    @Schema(name = "街道", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String street;

    @Schema(name = "详细地址", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String address;

    @Schema(name = "用户id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long userId;

}