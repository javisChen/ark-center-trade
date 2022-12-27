package com.ark.center.order;

import com.ark.component.web.config.ArkWebConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.ark.center.order.dao")
@SpringBootApplication
@EnableFeignClients(basePackages = {})
@EnableDiscoveryClient
public class Application extends ArkWebConfig {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}