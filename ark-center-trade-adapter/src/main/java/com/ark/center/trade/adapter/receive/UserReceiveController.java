package com.ark.center.trade.adapter.receive;

import com.ark.center.trade.application.user.UserReceiveAppService;
import com.ark.center.trade.client.receive.cmd.ReceiveCmd;
import com.ark.center.trade.client.receive.dto.ReceiveDTO;
import com.ark.center.trade.client.receive.query.UserReceivePageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户收货地址管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user/receive")
public class UserReceiveController extends BaseController {
    private final UserReceiveAppService userReceiveAppService;

    @Operation(summary = "分页查询用户收货地址")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<ReceiveDTO>> queryPages(@RequestBody @Validated UserReceivePageQry qry) {
        return SingleResponse.ok(userReceiveAppService.queryPages(qry));
    }

    @Operation(summary = "保存用户收货地址")
    @PostMapping("/save")
    public SingleResponse<Long> save(@RequestBody @Validated ReceiveCmd cmd) {
        return SingleResponse.ok(userReceiveAppService.save(cmd));
    }


}
