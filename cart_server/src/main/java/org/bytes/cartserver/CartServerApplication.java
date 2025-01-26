package org.bytes.cartserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = "org.bytes.cartserver.dao")
public class CartServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServerApplication.class, args);
    }
}
