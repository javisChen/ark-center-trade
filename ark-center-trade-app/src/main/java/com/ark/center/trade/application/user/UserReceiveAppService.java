package com.ark.center.trade.application.user;

import com.ark.center.trade.client.receive.cmd.ReceiveCmd;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.client.receive.query.UserReceivePageQry;
import com.ark.center.trade.domain.receive.gateway.UserReceiveGateway;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReceiveAppService {

    private final UserReceiveGateway userReceiveGateway;

    public PageResponse<ReceiveDTO> queryPages(UserReceivePageQry qry) {
        Long userId = ServiceContext.getCurrentUser().getUserId();
        qry.setUserId(userId);
        return userReceiveGateway.selectPages(qry);
    }

    public Long save(ReceiveCmd cmd) {
        cmd.setUserId(ServiceContext.getCurrentUser().getUserId());
        if (cmd.getId() != null) {
            return userReceiveGateway.update(cmd);
        } else {
            return userReceiveGateway.insert(cmd);
        }

    }
}
