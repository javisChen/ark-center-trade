package com.ark.center.trade.domain.order.model;

import lombok.Data;

/**
 * 规格属性DTO
 * @author jc
 */
@Data
public class SkuAttr {

    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 属性值
     */
    private String attrValue;
}
