package com.kt.cloud.order.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.orm.mybatis.base.BaseEntity;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kt.component.common.enums.BasicEnums;
import com.kt.component.common.enums.EnumUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@TableName("od_order")
public class OrderDO extends BaseEntity {


    /**
     * 订单号
     */
    @TableField("`code`")
    private String code;

    /**
     * 订单类型 enums[SHOP,商城订单,1]
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 下单渠道 enums[PC,PC,1;APP,APP,2;MINI,小程序,3]
     */
    @TableField("order_channel")
    private Integer orderChannel;

    /**
     * 订单状态 enums[PENDING_PAY,待支付,1;PENDING_DELIVER,待发货,2;PENDING_RECEIVE,待收货,3;PENDING_EVALUATE,待评价,4;SUCCESS,交易成功,5]
     */
    @TableField("order_status")
    private Integer orderStatus;

    /**
     * 支付状态 enums[PENDING_PAY,待支付,1;PAYING,支付中,2;PAY_SUCCESS,支付成功,3;PAY_FAIL,支付失败,4]
     */
    @TableField("pay_status")
    private Integer payStatus;

    /**
     * 支付类型 enums[WECHAT,微信支付,1;ALIPAY,支付宝,2]
     */
    @TableField("pay_type")
    private String payType;

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
     * 运费金额
     */
    @TableField("freight_amount")
    private Integer freightAmount;

    /**
     * 支付流水号
     */
    @TableField("pay_sn")
    private String paySn;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    @TableField("deliver_time")
    private LocalDateTime deliverTime;

    /**
     * 确认收货时间
     */
    @TableField("confirm_time")
    private LocalDateTime confirmTime;

    /**
     * 买家备注
     */
    @TableField("buyer_remark")
    private String buyerRemark;

    /**
     * 买家ID
     */
    @TableField("buyer_id")
    private Long buyerId;

    /**
     * 卖家ID
     */
    @TableField("seller_id")
    private Long sellerId;

    /**
     * 物流公司
     */
    @TableField("logistics_company")
    private String logisticsCompany;

    /**
     * 物流单号
     */
    @TableField("logistics_code")
    private String logisticsCode;

    @Getter
    @AllArgsConstructor
    public enum OrderType implements BasicEnums {
        SHOP(1, "商城订单"),
    ;
        private final Integer value;
        private final String text;

        public static OrderType getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
    @Getter
    @AllArgsConstructor
    public enum OrderChannel implements BasicEnums {
        PC(1, "PC"),
            APP(2, "APP"),
            MINI(3, "小程序"),
    ;
        private final Integer value;
        private final String text;

        public static OrderChannel getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
    @Getter
    @AllArgsConstructor
    public enum OrderStatus implements BasicEnums {
        PENDING_PAY(1, "待支付"),
            PENDING_DELIVER(2, "待发货"),
            PENDING_RECEIVE(3, "待收货"),
            PENDING_EVALUATE(4, "待评价"),
            SUCCESS(5, "交易成功"),
    ;
        private final Integer value;
        private final String text;

        public static OrderStatus getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
    @Getter
    @AllArgsConstructor
    public enum PayStatus implements BasicEnums {
        PENDING_PAY(1, "待支付"),
            PAYING(2, "支付中"),
            PAY_SUCCESS(3, "支付成功"),
            PAY_FAIL(4, "支付失败"),
    ;
        private final Integer value;
        private final String text;

        public static PayStatus getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
    @Getter
    @AllArgsConstructor
    public enum PayType implements BasicEnums {
        WECHAT(1, "微信支付"),
            ALIPAY(2, "支付宝"),
    ;
        private final Integer value;
        private final String text;

        public static PayType getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
}
