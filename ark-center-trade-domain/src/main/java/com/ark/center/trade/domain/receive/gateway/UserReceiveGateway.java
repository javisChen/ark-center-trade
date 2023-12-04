package com.ark.center.trade.domain.receive.gateway;

import com.ark.center.trade.client.receive.cmd.ReceiveCmd;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.client.receive.query.UserReceivePageQry;
import com.ark.center.trade.domain.order.OrderReceive;
import com.ark.component.dto.PageResponse;

public interface UserReceiveGateway {

    void save(OrderReceive orderReceive);

    Long insert(ReceiveCmd cmd);

    Long update(ReceiveCmd cmd);

    PageResponse<ReceiveDTO> selectPages(UserReceivePageQry qry);
}
