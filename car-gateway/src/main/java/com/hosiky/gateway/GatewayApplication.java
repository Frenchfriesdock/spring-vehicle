package com.hosiky.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * TODO 这个地方为啥是用包扫描的注解，我记得有一个配置文件可以来解决这个问题的呀？
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.hosiky.gateway", "com.hosiky.common"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
