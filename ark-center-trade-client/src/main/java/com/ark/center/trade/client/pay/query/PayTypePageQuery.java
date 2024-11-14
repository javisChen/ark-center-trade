package com.ark.center.trade.client.pay.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 支付类别表
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "PayTypePageQueryReqDTO对象")
public class PayTypePageQuery extends PagingQuery {


    @Schema(description = "支付类型名称")
    private String name;

    @Schema(description = "支付类型编号")
    private String code;

    @Schema(description = "支付单描述信息")
    private String description;

}
