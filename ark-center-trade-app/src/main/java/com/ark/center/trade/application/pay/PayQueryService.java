package com.ark.center.trade.application.pay;

import com.ark.center.trade.client.pay.dto.PayOrderCreateDTO;
import com.ark.center.trade.client.pay.query.PayOrderPageQuery;
import com.ark.component.dto.PageResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class PayQueryService {


    public Integer getPayOrderStatus(Long id) {
        return null;
    }

    public PageResponse<PayOrderCreateDTO> queryPages(PayOrderPageQuery queryDTO) {
        return null;
    }

    public PayOrderCreateDTO getPayOrderInfo(@NotNull(message = "id不能为空") Long id) {
        return null;
    }
}
