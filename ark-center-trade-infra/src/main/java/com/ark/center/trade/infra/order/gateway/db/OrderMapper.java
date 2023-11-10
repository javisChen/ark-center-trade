package com.ark.center.trade.infra.order.gateway.db;

import com.ark.center.trade.domain.order.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
public interface OrderMapper extends BaseMapper<Order> {

    int compareAndUpdateOrderStatusAndPayStatus(@Param("orderId") Long orderId,
                                                 @Param("sourceOrderStatus") Integer sourceOrderStatus,
                                                 @Param("targetOrderStatus") Integer targetOrderStatus,
                                                 @Param("payStatus") Integer payStatus);
}
