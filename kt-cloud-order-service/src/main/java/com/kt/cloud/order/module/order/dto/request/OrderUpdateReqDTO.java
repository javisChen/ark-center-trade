package com.kt.cloud.order.module.order.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kt.cloud.order.module.orderitem.dto.request.OrderItemUpdateReqDTO;
import com.kt.cloud.order.module.receive.dto.request.ReceiveCreateReqDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderUpdateReqDTO对象", description = "订单表")
public class OrderUpdateReqDTO implements Serializable {

    @ApiModelProperty(value = "订单类型 enums[SHOP,商城订单,1]", required = true)
    @NotNull(message = "订单类型 enums[SHOP,商城订单,1]不能为空")
    private Integer orderType;

    @ApiModelProperty(value = "下单渠道 enums[PC,PC,1;APP,APP,2;MINI,小程序,3]", required = true)
    @NotNull(message = "下单渠道 enums[PC,PC,1;APP,APP,2;MINI,小程序,3]不能为空")
    private Integer orderChannel;

    @ApiModelProperty(value = "订单状态 enums[PENDING_PAY,待支付,1;PENDING_DELIVER,待发货,2;PENDING_RECEIVE,待收货,3;PENDING_EVALUATE,待评价,4;SUCCESS,交易成功,5]", required = true)
    @NotNull(message = "订单状态 enums[PENDING_PAY,待支付,1;PENDING_DELIVER,待发货,2;PENDING_RECEIVE,待收货,3;PENDING_EVALUATE,待评价,4;SUCCESS,交易成功,5]不能为空")
    private Integer orderStatus;

    @ApiModelProperty(value = "买家ID", required = true)
    @NotNull(message = "买家ID不能为空")
    private Long buyerId;

    @ApiModelProperty(value = "买家备注", required = false)
    private String buyerRemark;

    @ApiModelProperty(value = "卖家ID", required = true)
    @NotNull(message = "卖家ID不能为空")
    private Long sellerId;

    @ApiModelProperty(value = "订单项", required = true)
    @Size(max = 1000, message = "订单项不能超过1000个")
    private List<OrderItemUpdateReqDTO> orderItems;

    @ApiModelProperty(value = "收货信息", required = true)
    private ReceiveCreateReqDTO receiveInfo;

}