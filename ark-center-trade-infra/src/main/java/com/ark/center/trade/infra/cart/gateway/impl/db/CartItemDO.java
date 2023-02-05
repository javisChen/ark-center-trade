package com.ark.center.trade.infra.cart.gateway.impl.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@TableName("od_cart_item")
public class CartItemDO extends BaseEntity {


    /**
     * 买家ID
     */
    @TableField("buyer_id")
    private Long buyerId;

    /**
     * SKU ID
     */
    @TableField("sku_id")
    private Long skuId;


    /**
     * SPU名称
     */
    @TableField("spu_name")
    private String spuName;

    /**
     * SKU单价
     */
    @TableField("price")
    private Integer price;

    /**
     * 购买数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 应付金额
     */
    @TableField("expect_amount")
    private Integer expectAmount;

    /**
     * 实付金额
     */
    @TableField("actual_amount")
    private Integer actualAmount;

    /**
     * 图片地址
     */
    @TableField("pic_url")
    private String picUrl;

    /**
     * SKU销售参数JSON
     */
    @TableField("spec_data")
    private String specData;

    /**
     * 是否选中 enums[YES,是,1;NO,否,0]
     */
    @TableField("checked")
    private Integer checked;

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
