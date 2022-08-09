package com.kt.cloud.order.module.orderitem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.order.dao.entity.OrderItemDO;
import com.kt.cloud.order.dao.mapper.OrderItemMapper;
import com.kt.cloud.order.module.orderitem.dto.request.OrderItemUpdateReqDTO;
import com.kt.cloud.order.module.orderitem.dto.request.OrderItemPageQueryReqDTO;
import com.kt.cloud.order.module.orderitem.dto.response.OrderItemRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;
import com.kt.component.orm.mybatis.base.BaseEntity;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Service
public class OrderItemService extends ServiceImpl<OrderItemMapper, OrderItemDO> implements IService<OrderItemDO> {

    public Long createOrderItem(OrderItemUpdateReqDTO reqDTO) {
        OrderItemDO entity = BeanConvertor.copy(reqDTO, OrderItemDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<OrderItemRespDTO> getPageList(OrderItemPageQueryReqDTO queryDTO) {
        IPage<OrderItemRespDTO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, OrderItemRespDTO.class));
        return BeanConvertor.copyPage(page, OrderItemRespDTO.class);
    }

    public Long updateOrderItem(OrderItemUpdateReqDTO reqDTO) {
        OrderItemDO entity = BeanConvertor.copy(reqDTO, OrderItemDO.class);
        updateById(entity);
        return entity.getId();
    }

    public OrderItemRespDTO getOrderItemInfo(Long OrderItemId) {
        OrderItemDO entity = getById(OrderItemId);
        return BeanConvertor.copy(entity, OrderItemRespDTO.class);
    }

}
