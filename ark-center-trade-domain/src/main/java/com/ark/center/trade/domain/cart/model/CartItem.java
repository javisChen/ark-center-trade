package com.ark.center.trade.domain.cart.model;

import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Data
public class CartItem {

    private Long id;

    /**
     * 买家ID
     */
    private Long buyerId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * SPU名称
     */
    private String spuName;

    /**
     * SKU单价
     */
    private Integer price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 应付金额
     */
    private Integer expectAmount;

    /**
     * 实付金额
     */
    private Integer actualAmount;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * SKU销售参数JSON
     */
    private String specData;

    /**
     * 是否选中 enums[YES,是,1;NO,否,0]
     */
    private Boolean checked;

    public void increase(int quantity) {
        this.quantity += quantity;
    }

    public void checked(Boolean checked) {
        this.checked = checked;
    }

    @Getter
    @AllArgsConstructor
    public enum Checked implements BasicEnums {
        YES(1, "是"),
        NO(0, "否"),
        ;
        private final Integer value;
        private final String text;

        public static Checked getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
}
