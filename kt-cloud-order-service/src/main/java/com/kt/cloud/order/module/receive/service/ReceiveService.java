package com.kt.cloud.order.module.receive.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.order.dao.entity.ReceiveDO;
import com.kt.cloud.order.dao.mapper.ReceiveMapper;
import com.kt.cloud.order.module.receive.dto.request.ReceiveUpdateReqDTO;
import com.kt.cloud.order.module.receive.dto.request.ReceivePageQueryReqDTO;
import com.kt.cloud.order.module.receive.dto.response.ReceiveRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;
import com.kt.component.orm.mybatis.base.BaseEntity;

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

}
