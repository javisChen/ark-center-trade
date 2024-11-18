//package com.ark.center.trade.infra.pay.service;
//
//import com.ark.center.trade.infra.pay.PayTypeDO;
//import com.ark.center.trade.infra.pay.db.PayTypeMapper;
//import com.ark.component.dto.PageResponse;
//import com.ark.component.orm.mybatis.base.BaseEntity;
//import com.ark.component.web.util.bean.BeanConvertor;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.stereotype.Service;
//
///**
// * <p>
// * 支付类别表 服务实现类
// * </p>
// *
// * @author EOP
// * @since 2022-08-11
// */
//@Service
//public class PayTypeService extends ServiceImpl<PayTypeMapper, PayTypeDO> implements IService<PayTypeDO> {
//
//    public Long createPayType(PayTypeUpdateReqDTO reqDTO) {
//        PayTypeDO entity = BeanConvertor.copy(reqDTO, PayTypeDO.class);
//        save(entity);
//        return entity.getId();
//    }
//
//    public PageResponse<PayTypeRespDTO> getPageList(PayTypePageQueryReqDTO queryDTO) {
//        IPage<PayTypeRespDTO> page = lambdaQuery()
//                .orderByDesc(BaseEntity::getGmtCreate)
//                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
//                .convert(item -> BeanConvertor.copy(item, PayTypeRespDTO.class));
//        return BeanConvertor.copyPage(page, PayTypeRespDTO.class);
//    }
//
//    public Long updatePayType(PayTypeUpdateReqDTO reqDTO) {
//        PayTypeDO entity = BeanConvertor.copy(reqDTO, PayTypeDO.class);
//        updateById(entity);
//        return entity.getId();
//    }
//
//    public PayTypeRespDTO getPayTypeInfo(Long PayTypeId) {
//        PayTypeDO entity = getById(PayTypeId);
//        return BeanConvertor.copy(entity, PayTypeRespDTO.class);
//    }
//
//}
