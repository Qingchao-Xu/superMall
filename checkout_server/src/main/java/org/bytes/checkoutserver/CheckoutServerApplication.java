package org.bytes.checkoutserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = "org.bytes.checkoutserver.dao")
public class CheckoutServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckoutServerApplication.class, args);
    }
}
