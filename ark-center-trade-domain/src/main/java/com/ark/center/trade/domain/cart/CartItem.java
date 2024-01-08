package com.ark.center.trade.domain.cart;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class CartItem extends BaseEntity {


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
     * 商品名称
     */
    @TableField("sku_name")
    private String skuName;

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
     * SKU规格参数JSON
     */
    @TableField("specs")
    private String specs;

    /**
     * 是否选中 enums[YES,是,1;NO,否,0]
     */
    @TableField("checked")
    private Boolean checked;


}
