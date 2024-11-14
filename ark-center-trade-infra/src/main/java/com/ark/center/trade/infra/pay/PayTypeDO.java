package com.ark.center.trade.infra.pay;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("pay_type")
public class PayTypeDO extends BaseEntity {


    /**
     * 支付类型名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 支付类型编号
     */
    @TableField("`code`")
    private String code;

    /**
     * 支付单描述信息
     */
    @TableField("`description`")
    private String description;

}
