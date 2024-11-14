package com.ark.center.trade.infra.pay.service;


import com.ark.center.trade.client.pay.command.PayNotifyRecordUpdateCommand;
import com.ark.center.trade.client.pay.dto.PayNotifyRecordDTO;
import com.ark.center.trade.client.pay.query.PayNotifyRecordPageQuery;
import com.ark.center.trade.infra.pay.PayNotifyRecordDO;
import com.ark.center.trade.infra.pay.db.PayNotifyRecordMapper;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付结果通知记录 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Service
public class PayNotifyRecordService extends ServiceImpl<PayNotifyRecordMapper, PayNotifyRecordDO> implements IService<PayNotifyRecordDO> {

    public Long createPayNotifyRecord(PayNotifyRecordUpdateCommand reqDTO) {
        PayNotifyRecordDO entity = BeanConvertor.copy(reqDTO, PayNotifyRecordDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<PayNotifyRecordDTO> queryPages(PayNotifyRecordPageQuery queryDTO) {
        IPage<PayNotifyRecordDTO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getCreateTime)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, PayNotifyRecordDTO.class));
        return BeanConvertor.copyPage(page, PayNotifyRecordDTO.class);
    }

    public Long updatePayNotifyRecord(PayNotifyRecordUpdateCommand reqDTO) {
        PayNotifyRecordDO entity = BeanConvertor.copy(reqDTO, PayNotifyRecordDO.class);
        updateById(entity);
        return entity.getId();
    }

    public PayNotifyRecordDTO getPayNotifyRecordInfo(Long PayNotifyRecordId) {
        PayNotifyRecordDO entity = getById(PayNotifyRecordId);
        return BeanConvertor.copy(entity, PayNotifyRecordDTO.class);
    }

}
