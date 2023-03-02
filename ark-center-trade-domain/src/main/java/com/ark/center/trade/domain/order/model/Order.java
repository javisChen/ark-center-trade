package com.ark.center.trade.domain.order.model;

import com.ark.center.trade.domain.order.model.vo.OrderAmount;
import com.ark.center.trade.domain.order.model.vo.OrderPay;
import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 订单类型 enums[SHOP,商城订单,1]
     */
    private Integer orderType;

    /**
     * 下单渠道 enums[PC,PC,1;APP,APP,2;MINI,小程序,3]
     */
    private Integer orderChannel;

    /**
     * 订单状态 enums[PENDING_PAY,待支付,1;PENDING_DELIVER,待发货,2;PENDING_RECEIVE,待收货,3;PENDING_EVALUATE,待评价,4;SUCCESS,交易成功,5]
     */
    private OrderStatus orderStatus;


    /**
     * 发货时间
     */
    private LocalDateTime deliverTime;

    /**
     * 确认收货时间
     */
    private LocalDateTime confirmTime;

    /**
     * 买家备注
     */
    private String buyerRemark;

    /**
     * 买家ID
     */
    private Long buyerId;

    /**
     * 卖家ID
     */
    private Long sellerId;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 物流单号
     */
    private String logisticsCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 订单金额信息
     */
    private OrderAmount orderAmount = new OrderAmount();

    /**
     * 订单
     */
    private OrderPay orderPay = new OrderPay();

    /**
     * 订单子项
     */
    private List<OrderItem> orderItemList;

    public void paySuccess(String payTradeNo, LocalDateTime payTime) {
        this.orderPay.setPayStatus(OrderPay.PayStatus.PAY_SUCCESS);
        this.orderPay.setPayTradeNo(payTradeNo);
        this.orderPay.setPayTime(payTime);
        this.orderStatus = OrderStatus.SUCCESS;
    }


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
        SUCCESS(4, "交易成功"),
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

}
