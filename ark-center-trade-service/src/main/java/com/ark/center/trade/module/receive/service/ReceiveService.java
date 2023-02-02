package com.ark.center.trade.module.receive.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ark.center.trade.dao.entity.ReceiveDO;
import com.ark.center.trade.dao.mapper.ReceiveMapper;
import com.ark.center.trade.module.receive.dto.request.ReceiveUpdateReqDTO;
import com.ark.center.trade.module.receive.dto.request.ReceivePageQueryReqDTO;
import com.ark.center.trade.api.response.ReceiveRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ark.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.component.orm.mybatis.base.BaseEntity;

/**
 * <p>
 * 订单收货信息 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Service
public class ReceiveService extends ServiceImpl<ReceiveMapper, ReceiveDO> implements IService<ReceiveDO> {

    public Long createReceive(ReceiveUpdateReqDTO reqDTO) {
        ReceiveDO entity = BeanConvertor.copy(reqDTO, ReceiveDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<ReceiveRespDTO> getPageList(ReceivePageQueryReqDTO queryDTO) {
        IPage<ReceiveRespDTO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, ReceiveRespDTO.class));
        return BeanConvertor.copyPage(page, ReceiveRespDTO.class);
    }

    public Long updateReceive(ReceiveUpdateReqDTO reqDTO) {
        ReceiveDO entity = BeanConvertor.copy(reqDTO, ReceiveDO.class);
        updateById(entity);
        return entity.getId();
    }

    public ReceiveRespDTO getReceiveInfo(Long ReceiveId) {
        ReceiveDO entity = getById(ReceiveId);
        return BeanConvertor.copy(entity, ReceiveRespDTO.class);
    }

    public ReceiveRespDTO getReceiveInfoByOrderId(Long orderId) {
        ReceiveDO receiveDO = lambdaQuery()
                .eq(ReceiveDO::getOrderId, orderId)
                .one();
        return BeanConvertor.copy(receiveDO, ReceiveRespDTO.class);
    }
}
