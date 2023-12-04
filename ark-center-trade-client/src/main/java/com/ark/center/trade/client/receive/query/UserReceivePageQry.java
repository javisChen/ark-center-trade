package com.ark.center.trade.client.receive.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "UserOrderPageQry", description = "用户收货地址查询")
public class UserReceivePageQry extends PagingQuery {

    @Schema(name = "用户id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long userId;
}
