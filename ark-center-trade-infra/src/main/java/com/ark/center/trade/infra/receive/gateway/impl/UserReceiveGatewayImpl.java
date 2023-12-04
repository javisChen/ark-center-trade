package com.ark.center.trade.infra.receive.gateway.impl;

import com.ark.center.trade.client.receive.cmd.ReceiveCmd;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.client.receive.query.UserReceivePageQry;
import com.ark.center.trade.domain.order.OrderReceive;
import com.ark.center.trade.domain.receive.UserReceive;
import com.ark.center.trade.domain.receive.gateway.UserReceiveGateway;
import com.ark.center.trade.infra.receive.assembler.UserReceiveAssembler;
import com.ark.center.trade.infra.receive.gateway.db.UserReceiveMapper;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReceiveGatewayImpl implements UserReceiveGateway {

    private final UserReceiveMapper userReceiveMapper;
    private final UserReceiveAssembler userReceiveAssembler;

    @Override
    public void save(OrderReceive orderReceive) {
        UserReceive userReceiveDO = userReceiveAssembler.convertToReceiveDO(orderReceive);
        userReceiveMapper.insert(userReceiveDO);
    }

    @Override
    public Long insert(ReceiveCmd cmd) {
        UserReceive userReceive = userReceiveAssembler.toReceive(cmd);
        userReceiveMapper.insert(userReceive);
        return userReceive.getId();
    }

    @Override
    public Long update(ReceiveCmd cmd) {
        UserReceive userReceive = userReceiveAssembler.toReceive(cmd);
        userReceiveMapper.updateById(userReceive);
        return userReceive.getId();
    }

    @Override
    public PageResponse<ReceiveDTO> selectPages(UserReceivePageQry qry) {

        LambdaQueryWrapper<UserReceive> qw = Wrappers.lambdaQuery(UserReceive.class)
                .eq(qry.getUserId() != null, UserReceive::getUserId, qry.getUserId())
                .orderByDesc(BaseEntity::getGmtCreate);

        IPage<ReceiveDTO> page = userReceiveMapper
                .selectPage(new Page<>(qry.getCurrent(), qry.getSize()), qw)
                .convert(userReceiveAssembler::convertToReceiveDTO);

        return PageResponse.of(page);
    }

}
