package com.ark.center.trade;

import com.ark.component.web.config.ArkWebConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = {
        "com.ark.center.trade.infra.*.gateway.db",
})
@SpringBootApplication
@EnableFeignClients()
@EnableDiscoveryClient
public class TradeApplication extends ArkWebConfig {

    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }

}