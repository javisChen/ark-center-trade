package com.ark.center.trade.domain.order.model.vo;

import lombok.Data;

/**
 * 订单金额信息
 */
@Data
public class OrderAmount {

    /**
     * 应付金额
     */
    private Integer expectAmount;

    /**
     * 实付金额
     */
    private Integer actualAmount;

    /**
     * 运费金额
     */
    private Integer freightAmount;
}
